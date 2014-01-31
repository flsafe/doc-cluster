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

(deftest test-vector-length
  (testing "Test vector length"
    (let [input-vector {"a" 1}
          expected-length 1.0]
      (is (= (vector-len input-vector)
             expected-length)))))

(deftest test-vector-length-sqrt-1
  (testing "Test vector length"
    (let [input-vector {"x" 1.0 "y" 0.0}
          expected-length (Math/sqrt 1.0)]
      (is (= (vector-len input-vector)
             expected-length)))))

(deftest test-normalize-vector
  []
  (let [input {"a" 0.5 "b" 0.75}
        expected {"a" 0.5547001962252291 "b" 0.8320502943378437}]
    (is (= (normalize-vector input)
            expected))))

(deftest test-cosine-similarity
  []
  (let [a {"x" 1.0 "y" 0.0}
        b {"x" 0.0 "y" 1.0}]
    (is (= (cosine-similarity a b) 0.0))))

(deftest test-cosine-similarity-45
  []
  (let [a {"x" 1.0 "y" 1.0}
        b {"x" 1.0 "y" 0.0}]
    (is (= (cosine-similarity a b) 0.7071067811865475))))