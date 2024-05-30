(ns app.core
  (:require
   [app.intervals :as ai]
   [uix.core :as uix :refer [defui $]]
   [uix.dom]))

(defui header []
  ($ :.header
     ($ :h1 "Intervals trainer")))

(defui button [{:keys [item]}]
  ($ :button {} item))

(defui randomizer []
  ($ :.centered.py-100
     ($ :div.mx-5 (ai/random-tone))
     ($ :div.mx-5 (ai/random-tone))))

(defui selector [{:keys [items]}]
  ($ :.centered
     (for [item items]
       ($ button {:key item :item item}))))

(defui app []
  ($ :.app
     ($ header)
     ($ randomizer)
     ($ selector {:items ai/semitones})))

(defonce root
  (uix.dom/create-root (js/document.getElementById "root")))

(defn render []
  (uix.dom/render-root ($ app) root))

(defn ^:export init []
  (render))
