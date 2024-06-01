(ns app.state
  (:require [app.intervals :as ai]
            [uix.core :as uix :refer [defui $]]))

(def initial-state {:score {:ok 0 :total 0}
                    :tones {:a (ai/random-tone)
                            :b (ai/random-tone)}})

(def state-ctx (uix/create-context nil))
(def dispatch-ctx (uix/create-context nil))

(defn reducer [state {:keys [type]}]
  (case type
    :randomize!
    (-> state
        (assoc :tones {:a (ai/random-tone) :b (ai/random-tone)})
        (update-in [:score :total] inc))

    state))

(defn use-dispatch []
  (uix/use-context dispatch-ctx))

(defui Provider [{:keys [children]}]
  (let [[state dispatch] (uix/use-reducer reducer initial-state)]
    ($ state-ctx.Provider {:value state}
       ($ dispatch-ctx.Provider {:value dispatch}
          children))))
