(ns app.events
  (:require
   [app.intervals :as app.intervals]
   [app.store :as app.store]
   [re-frame.core :as rf]))

(rf/reg-event-fx
 :initialise-db
 []
 (fn [_ _]
   {:db app.store/default-db}))

(rf/reg-event-db
 :toggle-randomize
 (fn [db _]
   (update db :randomize? not)))

(rf/reg-event-db
 :next
 (fn [db _]
   (-> db
       (assoc :attempts [])
       (assoc-in [:tones :a] (app.intervals/random-tone))
       (assoc-in [:tones :b] (app.intervals/random-tone))
       (assoc-in [:tones :quality] (app.intervals/random-quality)))))

(rf/reg-event-db
 :set-mode
 (fn [db [_ mode]]
   (assoc db :mode mode)))

(rf/reg-event-db
 :primary
 (fn [db _]
   (-> db
       (update :success inc)
       (update :attempts conj :primary))))

(rf/reg-event-db
 :error
 (fn [db _]
   (-> db
       (update :error inc)
       (update :attempts conj :error))))
