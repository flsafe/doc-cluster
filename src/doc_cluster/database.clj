(ns doc-cluster.database
  (:gen-class)
  (:require [clojure.java.jdbc :as j]))

(def config
  ((read-string (slurp "config.clj")) (or (keyword (System/getProperty "CLUSTER_ENV")) :dev)))

(def db (:db config))

(def docs-count
  (first
    (j/query db ["SELECT COUNT(*) as count from ads"] :row-fn :count)))

(defn to-doc
  [row]
  {:text (:text row)
   :class (:business_id row)})

(defn get-docs
  []
  (j/query db
           ["SELECT id, business_id, text
               FROM ads
               WHERE processed = true
               AND text IS NOT NULL
               AND text <> ''
               AND business_id = 81309"]
           :row-fn to-doc))

(defn business-docs
  []
  (j/query db
           ["SELECT id, business_id, text
             FROM ads
             WHERE processed = true
             AND text IS NOT NULL
             AND text <> ''
             AND business_id IN
              (81309, 32479, 34349, 36015, 42277, 33602, 34347, 33930, 34373, 48251, 38703, 34344, 50900, 39962, 76243, 34341, 33599)"]
           :row-fn to-doc))
