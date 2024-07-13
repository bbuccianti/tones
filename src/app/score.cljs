(ns app.score
  (:require [app.state :as state]
            [uix.core :as uix :refer [defui $]]))

(defui score []
  (let [{:keys [success error]} (app.state/use-state)]
    ($ :.row
       ($ :.col.is-right
          (str success " / " (+ success error))))))
