(ns server.sandbox.routes
  (:use compojure.core
        [server.sandbox.templates :only [main-template]]
        [server.sandbox.config :only [application-config]])
  (:require [compojure.route :as route]
            [compojure.response :as response]))

(def root
  (if (= "dev" (get-in (application-config) [:nomad/environment]))
    ""
    "public"))

(defroutes base-routes
  (GET "/" [] (main-template))
  (GET "/test" []
       {:status 200
        :headers {"Content-Type" "text/html; charset=utf-8"}})
  (GET "/api" [x y :as r]
       {:status 200
        :headers {"Content-Type" "text/html; charset=utf-8"}
        :body (str "OK: " r)})
  (route/resources "/" {:root root})
  (route/not-found "Page not found"))
