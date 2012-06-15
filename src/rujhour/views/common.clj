(ns rujhour.views.common
  (:use [noir.core :only [defpartial]])
  (:require [hiccup.core :as core]
            [hiccup.util :as util]
            [hiccup.page :as page]))

(defpartial layout [& content]
  (page/html5
    [:head
     [:title "ruj hour"]
     (page/include-css "/css/reset.css")]
    [:body
     [:div#wrapper
      content]]))

(defn- map-uri []
  (let [apikey (or (System/getenv "gmkey") (throw (IllegalStateException. "No API key defined")))]
    (str "http://maps.googleapis.com/maps/api/js" {:key apikey :sensor false})))
  
(defn- mapscripts []
  (let [apikey (or (System/getenv "gmkey") (throw (IllegalStateException. "No API key defined")))
        apiurl (util/url "http://maps.googleapis.com/maps/api/js" {:key apikey :sensor false})]
    (page/include-js apiurl "/js/maps.js")))

(defpartial html5-layout [& content]
  (page/html5
    content))

(defpartial site-layout [& content]
  (page/html5
    [:head
     [:title "Kjetil's site"]
     (page/include-css "/css/reset.css")]
    [:body
     (mapscripts)
     [:div {:id "wrapper"} content]]))