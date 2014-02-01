(defproject ad-text-cluster "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"],
                 [org.clojure/java.jdbc "0.3.0-beta2"]
                 [mysql/mysql-connector-java "5.1.25"]
                 [incanter "1.5.4"]
                 [org.clojure/math.combinatorics "0.0.7"]]
  :jvm-opts ["-Xmx6g"]
  :main doc-cluster.runner
  :profiles {:uberjar {:aot :all}})
