(ns server.sandbox.middleware
  (:use [taoensso.timbre :as timbre]))

(timbre/refer-timbre)

(defn- color-status [code]
  (let [color-map {2 :green
                   3 :blue
                   4 :yellow
                   5 :red}
        color (color-map (quot (Integer/parseInt code) 100))]
    (color-str color code)))

(defn wrap-request-logging [handler]
  (fn [{:keys [request-method ^String uri] :as req}]
    (let [start (System/currentTimeMillis)
          resp (handler req)
          finish (System/currentTimeMillis)
          code (str (:status resp))]
      (info (name request-method) (color-status code)
            (if-let [qs (:query-string req)]
              (str uri "?" qs) uri)
            (str (- finish start) "ms"))
      resp)))

(defn wrap-failsafe [handler]
  (fn [req]
    (try (handler req)
         (catch Exception e
           (error e "error handling request" req)
           ;; provide a better page for 500 here
           {:status 500 :body "Sorry, an error occured."}))))



















