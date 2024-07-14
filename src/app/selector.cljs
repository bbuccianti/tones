(ns app.selector
  (:require [app.intervals :as app.intervals]
            [app.store :as app.store]
            [re-frame.core :as rf]
            [uix.core :as uix :refer [defui $]]))

(defui mode-button [{:keys [id text]}]
  (let [mode (app.store/use-subscribe [:mode])]
    ($ :button.button
       {:class (if (= mode id) :primary :dark)
        :onClick
        (fn [] (rf/dispatch [:set-mode id]))}
       text)))

(defui modes []
  ($ :.container
     ($ mode-button {:id :intervals :text "Intervals"})
     ($ mode-button {:id :chords :text "Chords"})))

(defui button [{:keys [item check-fn]}]
  (let [tones (app.store/use-subscribe [:tones])
        [color set-color!] (uix/use-state nil)]
    (uix/use-effect (fn [] (set-color! nil)) [tones])
    ($ :button.button.mx-4.my-2.py-4
       {:class color
        :onClick
        (fn []
          (let [kw (check-fn tones item)]
            (when (not (= color kw))
              (rf/dispatch [kw]))
            (set-color! kw)))}
       item)))

(defn mode->functions [mode]
  (if (= mode :intervals)
    [app.intervals/semitones app.intervals/check-distance]
    [app.intervals/tones app.intervals/check-if-chord]))

(defui buttons []
  (let [mode             (app.store/use-subscribe [:mode])
        randomize?       (app.store/use-subscribe [:randomize?])
        [items check-fn] (mode->functions mode)]
    ($ :#buttons.py-5.container
       (for [item (if randomize? (shuffle items) items)]
         ($ button {:key      item
                    :check-fn check-fn
                    :item     item})))))

