(ns client.sandbox.core
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [client.sandbox.misc :as misc]))

(enable-console-print!)

(defonce app-state
  (atom {:nav {:header "Header"
               :links [{:title "Link 1"
                        :href "/"}
                       {:title "Link 2"
                        :href "/app"}]}}))

(println app-state)

(defn nav-header [data owner]
  (reify
    om/IRender
    (render [this]
      (dom/div (misc/class "-col2")
               (dom/div (misc/class "-header") data)))))

(defn nav-link [{:keys [href title] :as data} owner]
  (reify
    om/IRender
    (render [this]
      (dom/li nil
              (dom/a #js {:href href} title)))))

(defn nav [{:keys [nav] :as data} owner]
  (reify
    om/IRenderState
    (render-state [_ _]
      (dom/div (misc/class "-navbar" "-primary-")
               (dom/div (misc/class "-row")
                        (om/build nav-header (:header nav))
                        (dom/div (misc/class "-col3")
                                 (apply dom/ul (misc/class "-menu")
                                        (om/build-all nav-link (:links nav)))))))))

(om/root nav app-state
         {:target (. js/document (getElementById "app"))})

