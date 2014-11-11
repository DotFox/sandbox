(ns server.sandbox.core
  (:use [server.sandbox.routes :only [base-routes]]
        [server.sandbox.config :only [application-config]]
        [server.sandbox.middleware :only [wrap-request-logging
                                          wrap-failsafe]]
        [org.httpkit.server :only [run-server]]
        [ring.middleware.gzip]
        (ring.middleware [reload :only [wrap-reload]]
                         [defaults :refer :all])))

(def in-dev?
  (let [environment (get-in (application-config) [:nomad/environment])]
    (= environment "dev")))

(defn -main [& args]
  (let [base-handler (-> #'base-routes
                        wrap-failsafe
                        wrap-gzip)
        handler (if in-dev?
                  (-> base-handler
                     wrap-request-logging
                     wrap-reload)
                  base-handler)
        server-opts (get-in (application-config) [:server])]
    (println (str "Start server with " server-opts))
    (run-server (wrap-defaults handler site-defaults) server-opts)))














