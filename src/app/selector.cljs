(ns app.selector
  (:require [app.intervals :as app.intervals]
            [app.state :as app.state]
            [uix.core :as uix :refer [defui $]]))

(defui mode-button [{:keys [id text]}]
  (let [{:keys [mode]} (app.state/use-state)
        dispatch (app.state/use-dispatch)]
    ($ :button.button
       {:class (if (= mode id) :primary :dark)
        :onClick
        (fn []
          (dispatch [{:id :switch-mode :payload id}]))}
       text)))

(defui modes []
  ($ :.container
     ($ mode-button {:id :intervals :text "Intervals"})
     ($ mode-button {:id :chords :text "Chords"})))

(defui button [{:keys [item check-fn]}]
  (let [{:keys [tones]} (app.state/use-state)
        dispatch (app.state/use-dispatch)
        [color set-color!] (uix/use-state nil)]
    (uix/use-effect (fn [] (set-color! nil)) [tones])
    ($ :button.button.mx-4.my-2.py-4
       {:class color
        :onClick
        (fn []
          (let [kw (check-fn tones item)]
            (when (not (= color kw))
              (dispatch [{:id kw}]))
            (set-color! kw)))}
       item)))

(defn mode->functions [mode]
  {:items    (if (= mode :intervals)
               app.intervals/semitones
               app.intervals/tones)
   :check-fn (if (= mode :intervals)
               app.intervals/check-distance
               app.intervals/check-if-chord)})

(defui buttons []
  (let [{:keys [mode randomize?]} (app.state/use-state)
        {:keys [items check-fn]} (mode->functions mode)]
    ($ :#buttons.py-5.container
       (for [item (if randomize? (shuffle items) items)]
         ($ button {:key item
                    :check-fn check-fn
                    :item item})))))

