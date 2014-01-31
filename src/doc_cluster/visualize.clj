(ns doc-cluster.visualize
  (:gen-class)
  (:require [doc-cluster.core :as dc])
  (:require [doc-cluster.database :as db])
  (:require [clojure.math.combinatorics :as combo]))

(use '(incanter core charts stats))

(defn get-doc-dataset
  []
  (map vec
       (combo/combinations (remove #(empty? (:vector %))
                                   (-> (db/get-docs) (dc/doc-vectors)))
                           2)))

(view
  (histogram (map (fn [[doc1 doc2]]
                        (dc/cosine-similarity (:vector doc1) (:vector doc2)))
             (get-doc-dataset))))
