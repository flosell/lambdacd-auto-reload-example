(ns sb-pipeline.ring-reload
  (:require [sb-pipeline.core :as core]))

(defonce pipeline-atom
         (atom nil))

(defn- shutdown-old-pipeline [old-pipeline]
  (if-let [old-ctx (:context old-pipeline)]
    ((:shutdown-sequence (:config old-ctx)) old-ctx)))

(def app
  (let [_ (shutdown-old-pipeline @pipeline-atom)
        {routes    :routes
         pipeline :pipeline} (core/start-everything)]
    (reset! pipeline-atom pipeline)
    routes))
