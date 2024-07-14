(ns app.core
  (:require
   [app.config :as app.config]
   [app.score :as app.score]
   [app.selector :as app.selector]
   [app.tones :as app.tones]
   [re-frame.core :as rf]
   [uix.core :as uix :refer [defui $]]
   [uix.dom]
   [app.subs]
   [app.events]))

(defui app []
  ($ :.container
     ($ :h1.is-center "Tones")

     ($ app.selector/modes)

     ($ app.score/score)

     ($ app.tones/tones-or-chord)

     ($ app.selector/buttons)

     ($ app.config/randomizer)))

(defonce root
  (uix.dom/create-root (js/document.getElementById "root")))

(defn render []
  (uix.dom/render-root ($ app) root))

(defn ^:export init []
  (rf/dispatch-sync [:initialise-db])
  (render))
