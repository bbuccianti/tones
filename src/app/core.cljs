(ns app.core
  (:require
   [app.intervals :as ai]
   [uix.core :as uix :refer [defui $]]
   [uix.dom]))

(defui header []
  ($ :h1.is-center "Intervals trainer"))

(defui randomizer []
  ($ :.row.is-center.py-5
     ($ :.col-2
        ($ :.tag.is-large.is-center.py-5 (ai/random-tone)))
     ($ :.col-2
        ($ :.tag.is-large.is-center.py-5 (ai/random-tone)))))

(defui selector [{:keys [items]}]
  ($ :.container.is-center
     (for [item items]
       ($ :button.button {:key item} item))))

(defui app []
  ($ :.container
     ($ header)
     ($ randomizer)
     ($ selector {:items ai/semitones})))

(defonce root
  (uix.dom/create-root (js/document.getElementById "root")))

(defn render []
  (uix.dom/render-root ($ app) root))

(defn ^:export init []
  (render))
