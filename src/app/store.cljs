(ns app.store
  (:require ["use-sync-external-store/with-selector" :refer [useSyncExternalStoreWithSelector]]
            [reagent.impl.component :as impl.component]
            [reagent.ratom :as ratom]
            [scheduler]
            [app.intervals :as app.intervals]
            [uix.core :as uix]
            [re-frame.core :as rf]))

(def default-db {:success    0
                 :error      0
                 :mode       :intervals
                 :attempts   []
                 :randomize? false
                 :tones      {:a       (app.intervals/random-tone)
                              :b       (app.intervals/random-tone)
                              :quality (app.intervals/random-quality)}})

(defn- cleanup-ref [ref]
  (when-let [^ratom/Reaction temp-ref (aget ref "__rat")]
    (remove-watch ref temp-ref)
    (when-let [watching (.-watching temp-ref)]
      (set! (.-watching temp-ref)
            (.filter watching #(not (identical? ref %)))))))

(defn- use-batched-subscribe
  [^js ref]
  (uix/use-callback
   (fn [listener]
     ;; Adding an atom holding a set of listeners on a ref
     (let [listeners (or (.-react-listeners ref) (atom #{}))]
       (set! (.-react-listeners ref) listeners)
       (swap! listeners conj listener))
     (fn []
       (let [listeners (.-react-listeners ref)]
         (swap! listeners disj listener)
         ;; When the last listener was removed,
         ;; remove batched updates listener from the ref
         (when (empty? @listeners)
           (cleanup-ref ref)
           (set! (.-react-listeners ref) nil)))))
   [ref]))

(defn- use-sync-external-store [subscribe get-snapshot]
  (useSyncExternalStoreWithSelector
   subscribe
   get-snapshot
   nil ;; getServerSnapshot, only needed for SSR
   identity ;; selector, not using, just returning the value itself
   =)) ;; value equality check

(defn- run-reaction [^js ref]
  (let [key "__rat"
        ^js rat (aget ref key)
        on-change (fn [_]
                    ;; When the ref is updated, schedule all listeners in a batch
                    (when-let [listeners (.-react-listeners ref)]
                      (scheduler/unstable_scheduleCallback scheduler/unstable_ImmediatePriority
                                                           #(doseq [listener @listeners]
                                                              (listener)))))]
    (if (nil? rat)
      (ratom/run-in-reaction
       #(-deref ref) ref key on-change {:no-cache true})
      (._run rat false))))

(defn use-reaction [reaction]
  (if impl.component/*current-component*
    @reaction
    (let [subscribe (use-batched-subscribe reaction)
          get-snapshot (uix/use-callback #(run-reaction reaction) [reaction])]
      (use-sync-external-store subscribe get-snapshot))))

(defn use-subscribe [query]
  (let [sub (binding [ratom/*ratom-context* #js {}]
              (rf/subscribe query))
        ref (or sub (atom nil))]
    (use-reaction ref)))

(defn reducer [initial-state events]
  (println "EVENTS" events "INITIAL_STATE" initial-state)
  (reduce
   (fn [current-state {:keys [id payload]}]
     (println "CURRENT_STATE" current-state)
     (case id
       :error
       (-> current-state
           (update :error inc)
           (update :attempts conj id))

       :primary
       (-> current-state
           (update :success inc)
           (update :attempts conj id))

       :next
       (-> current-state
           (assoc :attempts [])
           (assoc-in [:tones :a] (app.intervals/random-tone))
           (assoc-in [:tones :b] (app.intervals/random-tone))
           (assoc-in [:tones :quality] (app.intervals/random-quality)))

       :switch-mode
       (-> current-state
           (assoc :mode payload))

       :randomize?
       (update current-state :randomize? not)

       current-state))
   initial-state
   events))

(defn fetch [k]
  nil)
