(ns app.selector
  (:require [app.intervals :as ai]
            [app.randomizer :as ar]
            [uix.core :as uix :refer [defui $]]))

(defui button [{:keys [randomize! item]}]
  ($ :button.button
     {:onClick randomize!}
     item))

(defui buttons []
  (let [randomize! (uix/use-context ar/randomizer-ctx)]
    ($ :.container.is-center
       (for [item ai/semitones]
         ($ button {:key item :randomize! randomize! :item item})))))

