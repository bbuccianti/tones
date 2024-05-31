(ns app.selector
  (:require [app.intervals :as ai]
            [app.randomizer :as ar]
            [uix.core :as uix :refer [defui $]]))

(defui button [{:keys [item default-color]}]
  (let [{:keys [a b]} (uix/use-context ar/tones-ctx)
        [color set-color!] (uix/use-state default-color)]
    ($ :button.button
       {:class color
        :onClick
        (fn []
          (if (= (apply ai/distance (map keyword [a b]))
                 item)
            (set-color! "primary")
            (set-color! "error")))}
       item)))

(defui buttons []
  ($ :.container.is-center
     (for [item ai/semitones]
       ($ button {:key item
                  :item item
                  :default-color "secondary"}))))

