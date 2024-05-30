(ns test.intervals-test
  (:require [app.intervals :refer [number distance]]
            [cljs.test :refer [deftest is are]]))

(deftest equal-tones
  (are [a b] (= (number a) (number b))
    :Ab :G#
    :A# :Bb
    :B# :C
    :Cb :B
    :C# :Db
    :D# :Eb
    :E# :F
    :Fb :E
    :F# :Gb))

(deftest distance-in-semitones
  (are [a b result] (= result (distance a b))
    :Ab :A  1
    :A  :B  2
    :A  :C  3
    :E  :A  5
    :G  :C  5
    :F  :C  7
    :Ab :G# 12))


