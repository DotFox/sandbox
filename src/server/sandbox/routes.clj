(ns server.sandbox.routes
  (:use compojure.core
        [server.sandbox.templates :only [main-template]]
        [server.sandbox.config :only [application-config]])
  (:require [clojure.java [io :as io]]
            [compojure.route :as route]
            [compojure.response :as response]))

(def root
  (if (= "dev" (get-in (application-config) [:nomad/environment]))
    ""
    "public"))

(defn picsel []
  (io/file "resources/images/picsel.png"))

(def memo-picsel
  (memoize picsel))

(defroutes base-routes
  (GET "/" [] (main-template))
  (GET "/images/:name.png" [name x y :as r]
       {:status 200
        :body (memo-picsel)})
  (route/resources "/" {:root root})
  (route/not-found "Page not found"))
