(defproject sb-pipeline "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[lambdacd "0.12.1"]
                 [ring-server "0.4.0"]
                 [org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.logging "0.3.1"]
                 [org.slf4j/slf4j-api "1.7.22"]
                 [ch.qos.logback/logback-core "1.1.8"]
                 [ch.qos.logback/logback-classic "1.1.8"]

                 [lambdacd-git "0.2.0"]
                 [http-kit "2.2.0"]
                 [cheshire "5.7.0"]]
  :profiles {:uberjar {:aot :all}}
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler sb-pipeline.core/app
         :init sb-pipeline.core/start-pipelines}
  :main sb-pipeline.core)
