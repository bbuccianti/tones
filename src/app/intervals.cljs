(ns app.intervals)

(def tone-numbers {:A 0 :A# 1 :B 2 :C 3 :C# 4 :D 5 :D# 6 :E 7 :F 8 :F# 9 :G 10 :G# 11})
(def tones (map name (keys tone-numbers)))
(def semitones (range 12))
(def chords {:minor #{3 7} :major #{4 7}})

(defn tone->number [tone]
  (tone-numbers (keyword tone)))

(defn distance [a b]
  (let [ax (tone->number a)
        bx (tone->number b)
        plus (if (> ax bx) 12 0)]
    (+ (- bx ax) plus)))

(defn number->tone [n]
  (some (fn [[k v]]
          (when (= v n)
            k))
        tone-numbers))

(defn random-tone []
  (->> (rand-int (count semitones)) number->tone name))

(defn random-quality []
  (rand-nth [:minor :major]))

(defn check-distance [{:keys [a b]} c]
  (if (= (->> [a b]
              (map keyword)
              (apply distance))
         c)
    :primary
    :error))

(defn check-if-chord [{:keys [a quality]} c]
  (if ((quality chords) (distance a c))
    :primary
    :error))
