(ns sb-pipeline.ring-reload
  (:require [sb-pipeline.core :as core]))

; this stores the current pipeline and gives us access when reloading it
(defonce pipelines-atom
         (atom nil))

(defn- shutdown-old-pipelines [old-pipelines]
  (if-let [old-ctx (:context (:sb old-pipelines))]
    ((:shutdown-sequence (:config old-ctx)) old-ctx)))

(def app
  (let [_ (shutdown-old-pipelines @pipelines-atom)
        {routes    :routes
         pipelines :pipelines} (core/start-everything)]
    (reset! pipelines-atom pipelines)
    routes))
