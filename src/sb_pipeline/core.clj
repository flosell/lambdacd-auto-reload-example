(ns sb-pipeline.core
  (:require [sb-pipeline.pipeline :as pipeline]
            [ring.server.standalone :as ring-server]
            [lambdacd.ui.ui-server :as ui]
            [lambdacd.runners :as runners]
            [lambdacd.util :as util]
            [lambdacd.core :as lambdacd]
            [clojure.tools.logging :as log]

            [compojure.core :as compojure]
            [ring.util.response :refer [response not-found]]
            [lambdacd.event-bus :as event-bus]
            [lambdacd-git.core :as git]
            [clojure.core.async :as async]

            [sb-pipeline.slack :as slack])
  (:gen-class))

(defn failure-notifications [ctx]
  (let [subscription   (event-bus/subscribe ctx :step-finished)
        steps-finished (event-bus/only-payload subscription)]
    (async/go-loop []
      (let [event (async/<! steps-finished)]
        (when (= :failure (get-in event [:final-result :status]))
          (slack/send-message event)))
      (recur))))

(defn start-everything []
  (let [pipelines {:sb (lambdacd/assemble-pipeline pipeline/sb {:home-dir   "builds/sb"
                                                                :name       "hidden"
                                                                :max-builds 3
                                                                :ui-config  {:expand-active-default   true
                                                                             :expand-failures-default true}})}
        routes    (compojure/routes
                    (compojure/context "/sb" [] (ui/ui-for (:sb pipelines))))]
    (git/init-ssh!)
    (failure-notifications (:context (:sb pipelines)))
    (runners/start-one-run-after-another (:sb pipelines))
    {:routes    routes
     :pipelines pipelines}))

(defn -main [& args]
  (let [{routes    :routes
         pipelines :pipelines} (start-everything)]
    (ring-server/serve routes {:open-browser? true
                               :port          8080})))
