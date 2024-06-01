(ns app.reset
  (:require [app.state :refer [use-dispatch]]
            [uix.core :as uix :refer [defui $]]))

(defui Next []
  (let [dispatch (use-dispatch)]
    ($ :.container.my-5.is-center
       ($ :.row
          ($ :.col
             ($ :button.button.dark
                {:onClick (fn [] (dispatch {:type :randomize!}))}
                "Next"))))))
