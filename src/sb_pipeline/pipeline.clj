(ns sb-pipeline.pipeline
  (:use [lambdacd.steps.control-flow]
        [sb-pipeline.steps])
  (:require
    [lambdacd.steps.manualtrigger :as manualtrigger]
    [lambdacd-git.core :as git]))

(def sb
  `(
     (alias "Triggers"
            (either
              wait-for-git
              manualtrigger/wait-for-manual-trigger))
     (alias "Test and build"
            (with-workspace
              clone
              git/list-changes
              lein-test))
     (alias "Deploy"
            info)))
