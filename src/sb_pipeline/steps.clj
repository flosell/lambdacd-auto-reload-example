(ns sb-pipeline.steps
  (:require [lambdacd.steps.shell :as shell]
            [lambdacd-git.core :as git]))

(def remote "git@github.com:flosell/testrepo.git")

(defn wait-for-git [args ctx]
  (git/wait-for-git ctx remote))

(defn clone [args ctx]
  (git/clone ctx remote "master" (:cwd args)))

(defn lein-test [args ctx]
  (shell/bash ctx (:cwd args)
              "echo lein-test"
              "sleep 10"))

(defn info [args ctx]
  (shell/bash ctx (:cwd args)
              "echo deploy"
              "sleep 15"))
