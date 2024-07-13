(ns app.tones
  (:require [app.state :as app.state]
            [uix.core :as uix :refer [defui $]]))

(defui tag [text]
  ($ :.col-1.mx-5
     ($ :.tag.is-large.is-rounded.is-center.py-5
        text)))

(defui tones []
  (let [{{:keys [a b]} :tones} (app.state/use-state)]
    ($ :.row.is-center.py-5
       ($ tag a)
       ($ tag b))))

(defui tone []
  (let [{{:keys [a quality]} :tones} (app.state/use-state)]
    ($ :.row.is-center.py-5
       ($ tag a)
       ($ tag quality))))

(defui tones-or-chord []
  (let [{:keys [mode]} (app.state/use-state)]
    (case mode
      :intervals ($ tones)
      :chords ($ tone))))
