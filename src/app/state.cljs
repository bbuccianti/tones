(ns app.state
  (:require [app.intervals :as app.intervals]
            [uix.core :as uix :refer [defui $]]))

(def initial-state
  {:success    0
   :error      0
   :mode       :intervals
   :attempts   []
   :randomize? false
   :tones      {:a       (app.intervals/random-tone)
                :b       (app.intervals/random-tone)
                :quality (app.intervals/random-quality)}})

(def state-ctx (uix/create-context nil))
(def dispatch-ctx (uix/create-context nil))

(defn reducer [initial-state events]
  (reduce
   (fn [current-state {:keys [id payload]}]
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

(defn use-dispatch []
  (uix/use-context dispatch-ctx))

(defn use-state []
  (uix/use-context state-ctx))

(defui Provider [{:keys [children]}]
  (let [[state dispatch] (uix/use-reducer reducer initial-state)]
    ($ state-ctx.Provider {:value state}
       ($ dispatch-ctx.Provider {:value dispatch}
          children))))
