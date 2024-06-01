(ns app.core
  (:require
   [app.score :as ah]
   [app.selector :as as]
   [app.state :as state]
   [app.tones :as at]
   [uix.core :as uix :refer [defui $]]
   [uix.dom]))

(defui app []
  ($ :.container
     ($ :h1.is-center "Intervals trainer")
     ($ state/Provider
        ($ ah/score)
        ($ at/tones)
        ($ as/buttons))))

(defonce root
  (uix.dom/create-root (js/document.getElementById "root")))

(defn render []
  (uix.dom/render-root ($ app) root))

(defn ^:export init []
  (render))
