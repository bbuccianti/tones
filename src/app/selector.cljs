(ns app.selector
  (:require [app.intervals :as ai]
            [app.randomizer :as ar]
            [uix.core :as uix :refer [defui $]]))

(defui button [{:keys [item]}]
  (let [{:keys [a b]} (uix/use-context ar/tones-ctx)
        [color set-color!] (uix/use-state nil)]
    (uix/use-effect (fn [] (set-color! nil)) [a b])
    ($ :button.button
       {:class color
        :onClick
        (fn []
          (if (= (apply ai/distance (map keyword [a b]))
                 item)
            (set-color! "success")
            (set-color! "error")))}
       item)))

(defui buttons []
  ($ :.container.is-center
     (for [item ai/semitones]
       ($ button {:key item
                  :item item}))))

