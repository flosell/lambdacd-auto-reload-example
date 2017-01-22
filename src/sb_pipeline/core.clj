(ns sb-pipeline.core
  (:require [sb-pipeline.pipeline :as pipeline]
            [ring.server.standalone :as ring-server]
            [lambdacd.ui.ui-server :as ui]
            [lambdacd.runners :as runners]
            [lambdacd.core :as lambdacd]
            [compojure.core :as compojure]
            [lambdacd-git.core :as git])
  (:gen-class))

(defn start-everything []
  (let [pipeline (lambdacd/assemble-pipeline pipeline/sb {:home-dir   "builds/sb"
                                                          :name       "hidden"
                                                          :max-builds 3
                                                          :ui-config  {:expand-active-default   true
                                                                       :expand-failures-default true}})
        routes   (compojure/routes
                   (compojure/context "/sb" [] (ui/ui-for pipeline)))]
    (git/init-ssh!)
    (runners/start-one-run-after-another pipeline)
    {:routes   routes
     :pipeline pipeline}))

(defn -main [& args]
  (let [{routes :routes} (start-everything)]
    (ring-server/serve routes {:open-browser? true
                               :port          8080})))
