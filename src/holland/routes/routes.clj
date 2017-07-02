(ns holland.routes.routes
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [net.cgrand.enlive-html :as html]
            [net.cgrand.enlive-html :refer [deftemplate defsnippet]]))

;;functions

(defn parsejw [rer rei rea res ree rec]
  {:r rer, :i rei, :a rea, :s res, :e ree, :c rec})

;;template & snippets

(defsnippet part2 "public/part2.html"
	[:#part2content :> :*]
	[])

(defsnippet part3 "public/part3.html"
  [:#part3content :> :*]
  [])

(deftemplate home "public/index.html"
  	[]
 	[:part2] (html/substitute (part2))
  [:part3] (html/substitute (part3)))

(defroutes app-routes
  (GET "/" [] (home))
  (GET "/res/:rer/:rei/:rea/:res/:ree/:rec" [rer rei rea res ree rec]
  	(str (parsejw rer rei rea res ree rec)))
  (route/not-found "Not Found"))