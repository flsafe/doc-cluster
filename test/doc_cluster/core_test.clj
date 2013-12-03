(ns doc-cluster.core-test
  (:require [clojure.test :refer :all]
            [doc-cluster.core :refer :all]))

(deftest test-term-frequencies
  (testing "FIXME, I fail."
    (let [term-freqs (term-frequencies "aaaxx")]
      (is (= (term-freqs '(\a \a \a)) 1))
      (is (= (term-freqs '(\a \a \x)) 1)))))
