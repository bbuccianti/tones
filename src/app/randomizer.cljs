(ns app.randomizer
  (:require [app.intervals :as ai]
            [uix.core :as uix :refer [defui $]]))

(defn generate-tones []
  {:a (ai/random-tone) :b (ai/random-tone)})

(defonce initial-state (generate-tones))

(def random-tones-ctx (uix/create-context nil))
(def randomizer-ctx (uix/create-context nil))

(defui RandomTonesProvider [{:keys [children]}]
  (let [[state set-state!] (uix/use-state initial-state)]
    ($ random-tones-ctx.Provider {:value state}
       ($ randomizer-ctx.Provider {:value (fn [] (set-state! (generate-tones)))}
          children))))

(defui randomizer []
  (let [{:keys [a b]} (uix/use-context random-tones-ctx)]
    ($ :.row.is-center.py-5
       ($ :.col-2
          ($ :.tag.is-large.is-center.py-5 a))
       ($ :.col-2
          ($ :.tag.is-large.is-center.py-5 b)))))


