(ns app.config
  (:require [app.store :as app.store]
            [re-frame.core :as rf]
            [uix.core :as uix :refer [defui $]]))

(defui randomizer []
  (let [randomize? (app.store/use-subscribe [:randomize?])
        class (if randomize? :primary :default)]
    ($ :.container.is-center
       ($ :button.button
          {:class class
           :onClick (fn [] (rf/dispatch [:toggle-randomize]))}
          "Randomize?")
       ($ :button.button.outline
          {:onClick (fn [] (rf/dispatch [:next]))}
          "Next"))))
