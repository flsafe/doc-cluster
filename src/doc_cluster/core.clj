(ns doc-cluster.core
  (:gen-class)
  (:require [clojure.string :as string])
  (:import (java.lang Math)))

; Vocabulary
; df  - Document frequency
; idf - Inverse document frequency
; tf  - Term frequency

(defn word-chars
  [document]
  (->> document
      (re-seq #"\w")
      (apply str)
      string/lower-case))

(defn ngrams
  [document & {:keys [n] :or {n 3}}]
  (map #(apply str %)
        (partition n 1 (word-chars document))))

(defn term-frequencies
  ([document & {:keys [n] :or {n 3}}]
  (-> document
      word-chars
      (ngrams :n n)
      frequencies)))

(defn distinct-terms
  [documents & {:keys [n] :or {n 3}}]
  (into #{}
        (mapcat #(ngrams % :n n)
                  documents)))

(defn doc-frequencies
  [documents & {:keys [n] :or {n 3}}]
  (frequencies (mapcat #(distinct-terms [%] :n n) documents)))

(defn inverse-doc-frequency
  [term df ndocs]
  [term (. Math log (/ ndocs
                       (float df)))])

(defn inverse-doc-frequencies
  [documents & {:keys [n] :or {n 3}}]
  (let [ndocs (count documents)]
    (into {}
          (map (fn [[term df]]
                 (inverse-doc-frequency term df ndocs))
               (doc-frequencies documents :n n)))))

(defn vector-len
  [document-vector]
  (Math/sqrt (reduce + (map #(* % %)
                            (vals document-vector)))))

(defn normalize-vector
  [document-vector]
    (into {}
      (for [[term weight] document-vector] [term (/ weight (vector-len document-vector))])))

(defn doc-vectors
  [documents & {:keys [n] :or {n 3}}]
  (let [idf (inverse-doc-frequencies (map :text documents) :n n)]
    (map (fn [document]
           (let [tf-doc (term-frequencies (:text document) :n n)]
            (assoc document :vector (normalize-vector (into {} (for [[term tf] tf-doc] [term (* tf (idf term))]))))))
         documents)))

