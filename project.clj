(defproject sandbox "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :jvm-opts ["-Dnomad.env=dev"
             "-Dfile.encoding=UTF-8"
             "-Dsun.jnu.encoding=UTF-8"]

  :plugins [[lein-garden "0.2.1"]
            [lein-asset-minifier "0.2.0"]
            [lein-bower "0.5.1"]
            [lein-cljsbuild "1.0.3"]
            [lein-pdo "0.1.1"]]

  :dependencies [[org.clojure/clojure "1.7.0-alpha2"]
                 [javax.servlet/servlet-api "3.0-alpha-1"]
                 [compojure "1.2.0"]
                 [ring/ring-devel "1.3.1"]
                 [ring/ring-core "1.3.1"]
                 [http-kit "2.2.0-SNAPSHOT"]
                 [jarohen/nomad "0.7.0"]
                 [com.taoensso/timbre "3.3.1"]
                 [enlive "1.1.5"]
                 [com.cemerick/piggieback "0.1.4-SNAPSHOT"]

                 [org.clojure/clojurescript "0.0-2371"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [om "0.7.3"]]

  :cljsbuild {:builds {:main {:source-paths ["src/client"]
                              :compiler {:output-to "resources/public/js/sandbox.js"
                                         :language-in :ecmascript5
                                         :language-out :ecmascript5}}}}

  :profiles {:dev {:cljsbuild {:builds {:main {:source-paths ["src/client-brepl"]
                                               :compiler {:output-dir "resources/public/js/out"
                                                          :pretty-print true
                                                          :optimization :none
                                                          :source-map "resources/public/js/sandbox.js.map"}}}}}
             :prod {:jvm-opts ["-Dnomad.env=prod"]
                    :cljsbuild {:builds {:main {:compiler {:optimization :advanced
                                                           :pretty-print false
                                                           :preamble ["vendor/lib/react/react.min.js"]
                                                           :externs ["react/externs/react.js"]}}}}}}
  
  :bower-dependencies [[maxmertkit "git://github.com/maxmert/maxmertkit.git#master"]
                       [react "0.11.1"]]
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
                           ["run"]]]
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
            "inspect-dev" ["with-profile" "dev" "pprint"]})
