(ns rujhour.views.welcome
  (:require [rujhour.views.common :as common]
            [hiccup.util :as util]
            [net.cgrand.enlive-html :as enlive])
  (:use [noir.core :only [defpage]]
        [hiccup.core :only [html]]))

(defpage "/welcome" []
  (common/layout
    [:p "Welcome to ruj hour"]))

(defpage "/" []
  (common/site-layout
    [:h1 "This is my first page!"]
    [:p "Hope you like it"]))

(defn- map-uri []
  (let [apikey (or (System/getenv "gmkey") (throw (IllegalStateException. "No API key defined")))]
    (util/url "http://maps.googleapis.com/maps/api/js" {:key apikey :sensor false})))

(defn- transformed [template]
  (enlive/at template
    [:div#wrapper] 
    (enlive/content {:tag :span 
                     :attrs {:id "kjetilspan"}
                     :content "Hei der"})
  
    [:script#map-replace]
    (enlive/set-attr {:src (map-uri)})))
;  (enlive/transform 
;    template  

(defpage "/live" []
  (common/html5-layout 
    (let [template (enlive/html-resource "rujhour/views/herd_changes.html")]
      (enlive/emit* (transformed template)))))
