(ns client-brepl.core
  (:require [weasel.repl :as ws-repl]))


(if-not (ws-repl/alive?)
  (ws-repl/connect "ws://localhost:9001"
                   :verbose true
                   :print #{:repl :console}
                   :on-error #(print "Error! " %)
                   :on-open #(print "Web REPL connected!")
                   :on-close #(print "WebREPL closed!")))
