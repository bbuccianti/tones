(ns app.state
  (:require [app.intervals :as ai]
            [uix.core :as uix :refer [defui $]]))

(def initial-state {:score {:ok 0 :total 0}
                    :attempts 0
                    :tones {:a (ai/random-tone)
                            :b (ai/random-tone)}})

(def state-ctx (uix/create-context nil))
(def dispatch-ctx (uix/create-context nil))

(defn inc-ok-score-if-attempts-are-zero [state]
  (let [{:keys [attempts]} state]
    (if (= attempts 0)
      (update-in state [:score :ok] inc)
      (assoc state :attempts 0))))

(defn reducer [state {:keys [type]}]
  (case type
    :error
    (-> state (update :attempts inc))

    :randomize!
    (-> state
        (assoc :tones {:a (ai/random-tone) :b (ai/random-tone)})
        inc-ok-score-if-attempts-are-zero
        (update-in [:score :total] inc))

    state))

(defn use-dispatch []
  (uix/use-context dispatch-ctx))

(defui Provider [{:keys [children]}]
  (let [[state dispatch] (uix/use-reducer reducer initial-state)]
    ($ state-ctx.Provider {:value state}
       ($ dispatch-ctx.Provider {:value dispatch}
          children))))
