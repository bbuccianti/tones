(ns app.score
  (:require [app.store :as app.store]
            [uix.core :as uix :refer [defui $]]))

(defn percentage [s e]
  (if (or (= s 0) (= e 0))
    0
    (. (/ (* s 100) (+ s e)) toFixed 2)))

(defui score []
  (let [success (app.store/use-subscribe [:success])
        error (app.store/use-subscribe [:error])]
    ($ :.row
       ($ :.col.is-right
          (str success " / " (+ success error)))
       ($ :span
          (str "(" (percentage success error) " %)")))))
