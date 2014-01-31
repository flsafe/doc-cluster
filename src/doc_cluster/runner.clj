(ns doc-cluster.runner
  (:gen-class)
  (:require [doc-cluster.core :as dc])
  (:require [doc-cluster.database :as db]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (pr-str (dc/doc-vectors (db/get-docs)))))

