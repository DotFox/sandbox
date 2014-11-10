(ns server.sandbox.templates
  (:use [net.cgrand.enlive-html]
        [net.cgrand.reload :only [auto-reload]]
        [server.sandbox.config :only [application-config]]))

(auto-reload *ns*)

(def env-str
  (get-in (application-config) [:nomad/environment]))

(def meta-tags (html-resource "templates/head/meta.html"))
(def styles (html-resource (str "templates/head/" env-str "/styles.html")))
(def scripts (html-resource (str "templates/head/" env-str "/scripts.html")))

(defsnippet meta-tags "templates/head/meta.html"
  [:meta]
  [])

;; (defsnippet styles (src "templates/head/" env-str "/styles.html"))

(deftemplate main-template "templates/application.html"
  []
  :lockstep
  {[:head :title] (after (meta-tags))}
  [:head [:meta last-of-type]] (after (select styles [:link]))
  :lockstep
  {[:body [:div last-of-type]] (after (select scripts [:script]))}
  [:body [:div last-of-type]])
