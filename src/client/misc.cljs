(ns client.sandbox.misc
  (:use [clojure.string :only [join]]))

(defn display [show]
  (if show
    #js {}
    #js {:display "none"}))

(defn class [& class-str]
  #js {:className (join " " class-str)})
