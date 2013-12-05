(ns doc-cluster.core-test
  (:require [clojure.test :refer :all]
            [doc-cluster.core :refer :all]))

(deftest test-term-frequencies
  (testing "Test term frequencies"
    (let [term-freqs (term-frequencies "aaaxx")]
      (is (= (term-freqs '(\a \a \a)) 1))
      (is (= (term-freqs '(\a \a \x)) 1)))))

(deftest test-doc-frequencies
  (testing "Test document frequences"
    (let [documents '("aaa" "bbb")
          expected {'(\a \a \a) 1 '(\b \b \b) 1}]
      (is (= (doc-frequencies documents) expected)))))

(deftest test-inverse-doc-frequencies
  (testing "Test document frequences"
    (let [documents '("aaa" "aaabbb")
          expected {'(\b \b \b) 0.6931471805599453, '(\a \b \b) 0.6931471805599453, '(\a \a \b) 0.6931471805599453, '(\a \a \a) 0.0}]
      (is (= (inverse-doc-frequencies documents) expected)))))
