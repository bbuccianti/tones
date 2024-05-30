(ns test.intervals-test
  (:require [app.intervals :refer [distance number->tone]]
            [cljs.test :refer [deftest are]]))

(deftest distance-in-semitones
  (are [a b result] (= result (distance a b))
    :B :C  1
    :A :B  2
    :A :C  3
    :E :A  5
    :G :C  5
    :F :C  7
    :A :G# 11))

(deftest tones-from-numbers
  (are [n tone] (= tone (number->tone n))
    0  :A
    2  :B
    3  :C
    5  :D
    7  :E
    8  :F
    10 :G))
