(defproject sandbox "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :jvm-opts ["-Dfile.encoding=UTF-8"
             "-Dsun.jnu.encoding=UTF-8"]

  :repositories {"my.datomic.com" {:url "https://my.datomic.com/repo"
                                   :creds :gpg}}

  :plugins [[lein-garden "0.2.5"]
            [lein-asset-minifier "0.2.0"]
            [lein-bower "0.5.1"]
            [lein-cljsbuild "1.0.4-SNAPSHOT"]
            [lein-pdo "0.1.1"]]

  :dependencies [[org.clojure/clojure "1.7.0-alpha3"]
                 [javax.servlet/servlet-api "3.0-alpha-1"]
                 [compojure "1.2.1"]
                 [ring/ring-devel "1.3.1"]
                 [ring/ring-core "1.3.1"]
                 [ring/ring-defaults "0.1.2"]
                 [bk/ring-gzip "0.1.1"]
                 [http-kit "2.2.0-SNAPSHOT"]
                 [jarohen/nomad "0.7.0"]
                 [com.taoensso/timbre "3.3.1-1cd4b70"]
                 [enlive "1.1.5"]
                 [com.datomic/datomic-pro "0.9.5067"]

                 [org.clojure/clojurescript "0.0-2371"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [om "0.8.0-alpha1"]]

  :datomic {:schemas ["resources/datomic/schema" ["my-db.edn"]]}

  :cljsbuild {:builds {:main {:source-paths ["src/client"]
                              :compiler {:output-to "resources/public/js/sandbox.js"
                                         :language-in :ecmascript5
                                         :language-out :ecmascript5}}}}

  :profiles {:dev {:jvm-opts ["-Dnomad.env=dev"
                              "-Xmx1g"
                              "-Xms1g"]
                   :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
                   :dependencies [[org.clojure/tools.nrepl "0.2.6"]
                                  [com.cemerick/piggieback "0.1.4-SNAPSHOT"]
                                  [weasel "0.4.3-SNAPSHOT"]]
                   ;; For initializing new DB only
                   :datomic {:config "resources/datomic/dev-transactor-template.properties"
                             :db-uri "datomic:dev://localhost:4334/my-db"}
                   :plugins [[cider/cider-nrepl "0.8.0-SNAPSHOT"]]
                   :cljsbuild {:builds {:main {:source-paths ["src/client-brepl"]
                                               :compiler {:output-dir "resources/public/js/out"
                                                          :pretty-print true
                                                          :optimization :none
                                                          :source-map "resources/public/js/sandbox.js.map"}}}}}
             :prod {:jvm-opts ["-Dnomad.env=prod"
                               "-Xmx4g"
                               "-Xms4g"]
                    ;; For initializing new DB only
                    :datomic {:config "resources/datomic/cassandra-transactor-template.properties"
                              :db-uri "datomic:cass://localhost:4334/my-db"}
                    :cljsbuild {:builds {:main {:compiler {:optimization :advanced
                                                           :pretty-print false
                                                           :preamble ["vendor/lib/react/react.min.js"]}}}}}}

  :bower-dependencies [[maxmertkit "git://github.com/maxmert/maxmertkit.git#master"]
                       [react "0.11.2"]]
  :bower {:directory "resources/vendor/lib"}

  :minify-assets {:assets {"resources/public/css/sandbox.css" ["resources/vendor/lib/maxmertkit/build/css/maxmertkit.min.css"
                                                               "resources/styles/sandbox.css"]}
                  :options {:optimization :none}}

  :garden {:builds [{:source-paths ["src"]
                     :stylesheet styles.sandbox.core/screen
                     :compiler {:output-to "resources/styles/sandbox.css"
                                :pretty-print? true}}]}

  :main server.sandbox.core

  :aliases {"clean-dev" ["with-profile" "dev"
                         ["pdo"
                          ["cljsbuild" "clean"]
                          ["clean"]]]
            "launch-dev" ["with-profile" "dev"
                          ["pdo"
                           ["garden" "auto"]
                           ["cljsbuild" "auto"]
                           ["minify-assets" "watch"]
                           ["run"]
                           ["repl" ":headless"]]]
            "clean-prod" ["with-profile" "prod"
                          ["pdo"
                           ["cljsbuild" "clean"]
                           ["clean"]]]
            "launch-prod" ["with-profile" "prod"
                           ["do"
                            ["garden" "once"]
                            ["cljsbuild" "once"]
                            ["minify-assets"]
                            ["run"]]]
            "update-deps" ["with-profile" "dev"
                           ["do"
                            ["ancient" "upgrade" ":all" ":allow-all" ":interactive" ":check-clojure" ":aggressive" ":no-tests"]]]
            "inspect-dev" ["with-profile" "dev" "pprint"]})
