(ns app.tones
  (:require [app.store :as app.store]
            [uix.core :as uix :refer [defui $]]))

(defui tag [text]
  ($ :.col-1.mx-5
     ($ :.tag.is-large.is-rounded.is-center.py-5
        text)))

(defui tones []
  (let [{:keys [a b]} (app.store/use-subscribe [:tones])]
    ($ :.row.is-center.py-5
       ($ tag a)
       ($ tag b))))

(defui tone []
  (let [{:keys [a quality]} (app.store/use-subscribe [:tones])]
    ($ :.row.is-center.py-5
       ($ tag a)
       ($ tag quality))))

(defui tones-or-chord []
  (let [mode (app.store/use-subscribe [:mode])]
    (case mode
      :intervals ($ tones)

      ($ tone))))
