(ns app.selector
  (:require [app.intervals :as ai]
            [app.tones :refer [use-tones]]
            [app.state :refer [use-dispatch]]
            [uix.core :as uix :refer [defui $]]))

(defui button [{:keys [item]}]
  (let [{:keys [a b]} (use-tones)
        dispatch (use-dispatch)
        [color set-color!] (uix/use-state nil)]
    (uix/use-effect (fn [] (set-color! nil)) [a b])
    ($ :button.button.mx-4.my-2.py-4
       {:class color
        :onClick
        (fn []
          (let [kw (ai/result a b item)]
            (dispatch {:type kw})
            (set-color! kw)))}
       item)))

(defui buttons []
  ($ :#buttons.py-5.container
     (for [item ai/semitones]
       ($ button {:key item
                  :item item}))))

