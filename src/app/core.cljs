(ns app.core
  (:require
   [app.config :as app.config]
   [app.score :as app.score]
   [app.selector :as app.selector]
   [app.state :as app.state]
   [app.tones :as app.tones]
   [uix.core :as uix :refer [defui $]]
   [uix.dom]))

(defui app []
  ($ :.container
     ($ :h1.is-center "Tones")
     ($ app.state/Provider
        ($ app.selector/modes)

        ($ app.score/score)

        ($ app.tones/tones-or-chord)

        ($ app.selector/buttons)

        ($ app.config/randomizer))))

(defonce root
  (uix.dom/create-root (js/document.getElementById "root")))

(defn render []
  (uix.dom/render-root ($ app) root))

(defn ^:export init []
  (render))
