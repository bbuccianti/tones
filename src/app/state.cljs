(ns app.state
  (:require [app.intervals :as ai]
            [uix.core :as uix :refer [defui $]]))

(def initial-state {:success 0
                    :error 0
                    :tones {:a (ai/random-tone)
                            :b (ai/random-tone)}})

(def state-ctx (uix/create-context nil))
(def dispatch-ctx (uix/create-context nil))

(defn reducer [state {:keys [type]}]
  (case type
    :error
    (-> state (update :error inc))

    :success
    (-> state
        (assoc :tones {:a (ai/random-tone) :b (ai/random-tone)})
        (update :success inc))

    state))

(defn use-dispatch []
  (uix/use-context dispatch-ctx))

(defui Provider [{:keys [children]}]
  (let [[state dispatch] (uix/use-reducer reducer initial-state)]
    ($ state-ctx.Provider {:value state}
       ($ dispatch-ctx.Provider {:value dispatch}
          children))))
