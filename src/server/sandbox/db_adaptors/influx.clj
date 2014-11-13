(ns server.sandbox.db-adaptors.influx
  (:use [server.sandbox.config :only [application-config]])
  (:require [capacitor.core :as influx]))

(defonce influx-config
  (get-in (application-config) [:database/influx]))

(def client
  (influx/make-client influx-config))

(influx/create-db client)

(influx/post-points client "test"
                    [{:test "1"}
                     {:test "2"}])








