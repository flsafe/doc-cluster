(ns doc-cluster.core
  (:gen-class)
  (:require [clojure.string :as string])
  (:import (java.lang Math)))

(defn word-chars
  [s]
  (->> s
      (re-seq #"\w")
      (apply str)
      string/lower-case))

(defn ngrams
  [s & {:keys [n] :or {n 3}}]
  (map #(apply str %)
        (partition n 1 (word-chars s))))

(defn term-frequencies
  ([document & {:keys [n] :or {n 3}}]
  (-> document
      word-chars
      (ngrams :n n)
      frequencies)))

(defn distinct-terms
  [documents & {:keys [n] :or {n 3}}]
  (into #{}
    (for [doc-terms (map #(ngrams %  :n n) documents) term doc-terms]
      term)))

(defn doc-frequencies
  [documents & {:keys [n] :or {n 3}}]
  (frequencies (apply concat (map #(distinct-terms [%] :n n) documents))))

(defn inverse-doc-frequency
  [term term-df number-of-docs]
  [term (. Math log (/ number-of-docs
                       (float term-df)))])

(defn inverse-doc-frequencies
  [documents & {:keys [n] :or {n 3}}]
  (let [number-of-docs (count documents)]
    (into {}
          (map (fn [pair]
                (let [[term term-df] pair]
                  (inverse-doc-frequency term term-df number-of-docs)))
               (doc-frequencies documents :n n)))))

(defn doc-vectors
  [documents & {:keys [n] :or {n 3}}]
  (let [idf (inverse-doc-frequencies documents :n n)
        doc-term-freqs (map #(term-frequencies % :n n) documents)]
    (for [doc doc-term-freqs]
      (into {}
        (for [[term term-freq] doc] [term (* term-freq (idf term))])))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
