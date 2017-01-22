# LambdaCD lein-ring auto-reload example

An example of how to structure a LambdaCD project with support for auto-reloading source code on the fly with [lein-ring](https://github.com/weavejester/lein-ring). Makes sure pipelines are killed and restarted after a source code change in development.

## Usage

* `lein ring server` will start an auto-reloading pipeline
* `lein run` will start your pipeline with a web-ui listening on port 8080

## Files

* `/`
    * `project.clj` contains dependencies and build configuration for Leiningen

* `/src/sb_pipeline/`
    * `ring_reload.clj` contains the scaffolding for an auto-reloading ring-app
    * `pipeline.clj` contains your pipeline-definition
    * `steps.clj` contains your custom build-steps
    * `core.clj` contains the `main` function that wires everything together

* `/resources/`
    * `logback.xml` contains a sample log configuration


## Resources

* [LambdaCD Issue #153](https://github.com/flosell/lambdacd/issues/153)
