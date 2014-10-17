(ns server.sandbox.config
  (:require [nomad :refer [defconfig]]
            [clojure.java.io :as io]))

(defconfig application-config (io/resource "configs/sandbox.edn"))
