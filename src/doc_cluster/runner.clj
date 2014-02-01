(ns doc-cluster.runner
  (:gen-class)
  (:require [doc-cluster.core :as dc])
  (:require [doc-cluster.database :as db])
  (:require [clojure.math.combinatorics :as combo]))

(use '(incanter core charts stats))

(defn get-business-docs
  []
  (->(db/business-docs)
     (dc/doc-vectors :n 6)))

(defn group-by-class
  [bdocs]
  (->> bdocs
       (sort-by :class)
       (partition-by :class)))

(defn get-pair-groups
  []
  (map (fn [doc-group]
           (map vec
                (combo/combinations (distinct (map :vector doc-group))
                                     2)))
        (-> (get-business-docs)
            (group-by-class))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (doseq [pair-group (get-pair-groups)]
    (view
      (histogram
         (flatten
           (map (fn [[doc1 doc2]]
                  (dc/cosine-similarity doc1 doc2))
                 pair-group))))))

