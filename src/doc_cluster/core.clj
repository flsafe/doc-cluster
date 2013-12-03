(ns doc-cluster.core
  (:gen-class)
  (:require [clojure.string :as string])
  (:import (java.lang Math)))

(defn word-chars
  [s]
  (string/lower-case (apply str (re-seq #"\w" s))))

(defn ngrams
  [n s]
  (partition n 1 s))

(defn trigrams
  [document]
  (ngrams 3 document))

(defn term-frequencies
  [document]
  (frequencies (trigrams (word-chars document))))

(defn doc-frequencies
  [documents]
  (let [terms (distinct (for [doc-trigrams (map trigrams documents) term doc-trigrams] term))]
    (apply merge-with + {}
      (for [t terms d documents :when ((term-frequencies d) t)]
        {t 1}))))

(defn inverse-doc-frequency
  [term term-df number-of-docs]
    [term (. Math log (/ number-of-docs
                         (float term-df)))])

(defn inverse-doc-frequencies
  [documents]
  (let [number-of-docs (count documents)]
    (into {}
          (map (fn [pair]
                (let [[term term-df] pair]
                  (inverse-doc-frequency term term-df number-of-docs)))
               (doc-frequencies documents)))))

(defn doc-vectors
  [documents]
  (let [idf (inverse-doc-frequencies documents)
        term-freqs (map term-frequencies documents)]
    (into {}
      (for [doc term-freqs [term term-freq] doc]
        [term (* term-freq (idf term))]))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
