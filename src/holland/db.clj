(ns holland.db
  (:require [clj-postgresql.core :as pg]
            [clojure.java.jdbc :as jdbc]))

(def db2 (pg/pool :host "localhost:5432"
                  :user "oremines2"
                  :dbname "oremines2"
                  :password "admin2017"))

(defn alluser []
	(jdbc/query db2 [(str "select * from holland")]))

(defn login-f [user]
	(jdbc/query db2 [(str "select * from holland where username = '" user "'")]))