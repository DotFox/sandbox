(ns server.sandbox.core
  (:use [server.sandbox.routes :only [base-routes]]
        [server.sandbox.config :only [application-config]]
        [server.sandbox.middleware :only [wrap-request-logging
                                          wrap-failsafe]]
        [org.httpkit.server :only [run-server]]
        (ring.middleware [keyword-params :only [wrap-keyword-params]]
                         [params :only [wrap-params]]
                         [session :only [wrap-session]]
                         [reload :only [wrap-reload]])
        [compojure.handler :only [site]]))

(def in-dev?
  (let [environment (get-in (application-config) [:nomad/environment])]
    (= environment "dev")))

(defn -main [& args]
  (let [base-handler (-> #'base-routes
                        wrap-session
                        wrap-keyword-params
                        wrap-params
                        wrap-failsafe
                        site)
        handler (if in-dev?
                  (-> base-handler
                     wrap-request-logging
                     wrap-reload)
                  base-handler)
        server-opts (get-in (application-config) [:server])]
    (println (str "Start server with " server-opts))
    (run-server handler server-opts)))














