(ns server.sandbox.db-adaptors.cassandra
  (:require [clojurewerkz.cassaforte.client :as cc])
  (:use [server.sandbox.config :only [application-config]]))

(def DB
  (atom {}))

(defn prepare-db []
  (let [database (get-in (application-config) [:database :cassandra])
        conn (cc/connect (:hosts database))]
    (swap! DB assoc :connection conn)))

(defn write-event [])




















