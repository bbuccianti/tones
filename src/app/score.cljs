(ns app.score
  (:require [app.state :as state]
            [uix.core :as uix :refer [defui $]]))

(defn use-score []
  (let [state (uix/use-context state/state-ctx)]
    (select-keys state [:success :error])))

(defui score []
  (let [{:keys [success error]} (use-score)]
    ($ :.row
       ($ :.col.is-right
          (str success " / " (+ success error))))))
