(ns doc-cluster.core-test
  (:require [clojure.test :refer :all]
            [doc-cluster.core :refer :all]))

(deftest test-term-frequencies
  (testing "Test term frequencies"
    (let [term-freqs (term-frequencies "aaaxx")]
      (is (= (term-freqs "aaa") 1))
      (is (= (term-freqs "aax") 1)))))

(deftest test-doc-frequencies
  (testing "Test document frequences"
    (let [documents '("aaa" "bbb")
          expected {"aaa" 1 "bbb" 1}]
      (is (= (doc-frequencies documents) expected)))))

(deftest test-inverse-doc-frequencies
  (testing "Test document frequences"
    (let [documents '("aaa" "aaabbb")
          expected {"bbb" 0.6931471805599453, "abb" 0.6931471805599453, "aab" 0.6931471805599453, "aaa" 0.0}]
      (is (= (inverse-doc-frequencies documents) expected)))))
