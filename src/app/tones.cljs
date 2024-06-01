(ns app.tones
  (:require [app.state :as state]
            [uix.core :as uix :refer [defui $]]))

(defn use-tones []
  (let [state (uix/use-context state/state-ctx)]
    (:tones state)))

(defui tones []
  (let [{:keys [a b]} (use-tones)]
    ($ :.row.is-center.py-5
       ($ :.col-2
          ($ :.tag.is-large.is-center.py-5 a))
       ($ :.col-2
          ($ :.tag.is-large.is-center.py-5 b)))))
