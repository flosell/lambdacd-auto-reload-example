(ns sb-pipeline.slack
  (:require [clojure.tools.logging :as log]))

(defn send-message [ev]
  (log/info "Simulated slack message " ev))
