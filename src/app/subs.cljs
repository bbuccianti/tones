(ns app.subs
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 :tones
 (fn [db _]
   (:tones db)))

(rf/reg-sub
 :mode
 (fn [db _]
   (:mode db)))

(rf/reg-sub
 :randomize?
 (fn [db _]
   (:randomize? db)))

(rf/reg-sub
 :error
 (fn [db _]
   (:error db)))

(rf/reg-sub
 :success
 (fn [db _]
   (:success db)))
