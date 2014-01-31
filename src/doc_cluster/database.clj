(ns doc-cluster.database
  (:gen-class)
  (:require [clojure.java.jdbc :as j]))

(def config
  ((read-string (slurp "config.clj")) (or (keyword (System/getProperty "CLUSTER_ENV")) :dev)))

(def db (:db config))

(def docs-count
  (first
    (j/query db ["SELECT COUNT(*) as count from ads"] :row-fn :count)))

(defn get-docs
  []
  (j/query db ["SELECT id, business_id, text
                FROM ads
                WHERE processed = true AND text IS NOT NULL AND business_id IS NOT NULL
                AND text <> ''
                LIMIT 1000;"]
           :row-fn (fn [row]
                     {:text (:text row)
                      :class (:business_id row)})))
