(ns app.core
  (:require
   [app.randomizer :as ar]
   [app.selector :as as]
   [uix.core :as uix :refer [defui $]]
   [uix.dom]))

(defui app []
  ($ :.container
     ($ :h1.is-center "Intervals trainer")
     ($ ar/RandomTonesProvider
        ($ ar/randomizer)
        ($ as/buttons)
        ($ ar/reset))))

(defonce root
  (uix.dom/create-root (js/document.getElementById "root")))

(defn render []
  (uix.dom/render-root ($ app) root))

(defn ^:export init []
  (render))
