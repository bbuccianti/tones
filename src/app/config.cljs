(ns app.config
  (:require [app.state :as app.state]
            [uix.core :as uix :refer [defui $]]))

(defui randomizer []
  (let [{:keys [randomize?]} (app.state/use-state)
        dispatch (app.state/use-dispatch)
        class (if randomize? :primary :default)]
    ($ :.container.is-center
       ($ :button.button
          {:class class
           :onClick (fn [] (dispatch [{:id :randomize?}]))}
          "Randomize?")
       ($ :button.button.outline
          {:onClick (fn [] (dispatch [{:id :next}]))}
          "Next"))))
