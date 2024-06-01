(ns app.score
  (:require [app.state :as state]
            [uix.core :as uix :refer [defui $]]))

(defn use-score []
  (let [state (uix/use-context state/state-ctx)]
    (:score state)))

(defui score []
  (let [{:keys [ok total]} (use-score)]
    ($ :.row
       ($ :.col.is-right
          (str ok " / " total)))))
