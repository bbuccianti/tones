(ns app.intervals)

(def tones {:Ab 0  :A 1  :A# 2
            :Bb 2  :B 3  :B# 4
            :Cb 3  :C 4  :C# 5
            :Db 5  :D 6  :D# 7
            :Eb 7  :E 8  :E# 9
            :Fb 8  :F 9  :F# 10
            :Gb 10 :G 11 :G# 0})

(defn number [tone]
  (tones tone))

(defn distance [a b]
  (let [ax (number a)
        bx (number b)
        plus (if (>= ax bx) 12 0)]
    (+ (- bx ax) plus)))
