(ns app.intervals)

(def tone-numbers {:A 0 :A# 1 :B 2 :C 3 :C# 4 :D 5 :D# 6 :E 7 :F 8 :F# 9 :G 10 :G# 11})
(def semitones (range 0 12))
(def names {:unison 0 :minor-second 1 :major-second 2 :minor-third 3 :major-third 4 :perfect-fourth 5 :augmented-forth 6 :diminished-fifth 6 :perfect-fifth 7 :minor-sixth 8 :major-sixth 9 :minor-seventh 10 :major-seventh 11})

(defn number [tone]
  (tone-numbers tone))

(defn distance [a b]
  (let [ax (number a)
        bx (number b)
        plus (if (>= ax bx) 12 0)]
    (+ (- bx ax) plus)))

(defn number->tone [n]
  (some (fn [[k v]]
          (when (= v n)
            k))
        tone-numbers))

(defn random-tone []
  (->> (rand-int (count semitones)) number->tone name))
