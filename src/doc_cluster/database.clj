(ns doc-cluster.database
  (:gen-class)
  (:require [clojure.java.jdbc :as j]))

(def config
  ((read-string (slurp "config.clj")) (or (keyword (System/getProperty "CLUSTER_ENV")) :dev)))

(def db (:db config))

(def docs-count
  (first
    (j/query db ["SELECT COUNT(*) as count from ads"] :row-fn :count)))

(defn docs
  [pubid]
  (j/query db ["SELECT id, publisher_id, business_id, text FROM ads WHERE processed = true LIMIT 2000;"]))
