(ns client-brepl.core
  (:require [clojure.browser.repl :as repl]))

(repl/connect "http://localhost:9000/repl")