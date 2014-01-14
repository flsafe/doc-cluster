(ns doc-cluster.database
  (:gen-class)
  (:require [clojure.java.jdbc :as j]
            [doc-cluster.core :as d]))

(def config
  ((read-string (slurp "config.clj")) (or (keyword (System/getProperty "CLUSTER_ENV")) :dev)))

(def db (:db config))

(def docs-count
  (first
    (j/query db ["SELECT COUNT(*) as count from ads"] :row-fn :count)))

(defn get-docs
  []
  (j/query db
           ["SELECT id, publisher_id, business_id, text FROM ads WHERE processed = true LIMIT 2000;"]
           :row-fn (fn [row] {:text (:text row) :class (:business_id row)})))
