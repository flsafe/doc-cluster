(ns doc-cluster.core
  (:gen-class)
  (:require [clojure.string :as string])
  (:import (java.lang Math)))

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

(defn doc-vectors
  [documents & {:keys [n] :or {n 3}}]
  (let [df (doc-frequencies (map :text documents) :n n)
        ndocs (count documents)]
    (reduce (fn [result document]
              (conj result
                    {:class (:class document)
                     :vector (merge-with (fn [term-freq doc-freq]
                                           (* term-freq (. Math log (/ ndocs (float doc-freq)))))
                                         (term-frequencies (:text document) :n n)
                                         df)}))
            []
            documents)))

(defn vector-magnitude
  [document-vector]
  (Math/sqrt (reduce + (map #(* % %)
                            (vals document-vector)))))

(defn normalize-vector
  [document-vector]
    (into {}
      (for [[term weight] document-vector] [term (/ weight (vector-magnitude document-vector))])))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
