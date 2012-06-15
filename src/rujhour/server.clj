(ns rujhour.server
  (:require [clojure.stacktrace :as stacktrace] 
            [ring.adapter.netty :as netty]
            [noir.server :as server]))

(server/load-views "src/rujhour/views/")

(defn netty-server-start [port mode & [opts]]
  (let [netty-opts (merge {:port port} opts netty/default-server-options)]
    (println netty-opts)
    (netty/run-netty (server/gen-handler opts) netty-opts)))

(def server-starters
  {:jetty (fn [port mode]
            (server/start port {:mode mode :ns 'rujhour}))
   :netty (fn [port mode]
            (netty-server-start port mode))})

(defn -main [& m]
  (try 
    (let [[server-type mode] (map keyword m)
          server-starter ((or server-type :netty) server-starters)
          port (Integer. (get (System/getenv) "PORT" "8080"))]
      (println server-starter)
      (if server-starter
        (server-starter port (or mode :dev))
        (throw (IllegalStateException. (str "No such server type: " server-type)))))
    (catch Exception e 
      (stacktrace/print-cause-trace e))))
