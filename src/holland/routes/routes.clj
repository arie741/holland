(ns holland.routes.routes
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [net.cgrand.enlive-html :as html]
            [net.cgrand.enlive-html :refer [deftemplate defsnippet]]))

;;functions

(defn parsejw [rer rei rea res ree rec]
  {:r (Integer/parseInt rer), 
   :i (Integer/parseInt rei), 
   :a (Integer/parseInt rea), 
   :s (Integer/parseInt res), 
   :e (Integer/parseInt ree), 
   :c (Integer/parseInt rec)})

(defn mav [raw]
  (apply max (vals raw)))

(defn parseres [raw]
  (select-keys raw (for [[k v] raw :when (= v (mav raw))] k)))

;;template & snippets

(defsnippet part2 "public/part2.html"
	[:#part2content :> :*]
	[])

(defsnippet part3 "public/part3.html"
  [:#part3content :> :*]
  [])

(deftemplate rept "public/results.html"
  [res]
  [(keyword (str "div.pan-" (last (str (first (keys res))))))] (html/remove-class "hidden")
  [(keyword (str "div.pan-" (last (str (last (keys res))))))] (html/remove-class "hidden"))

(deftemplate home "public/index.html"
  []
  [:part2] (html/substitute (part2))
  [:part3] (html/substitute (part3)))

(defroutes app-routes
  (GET "/" [] (home))
  (GET "/res/:rer/:rei/:rea/:res/:ree/:rec" [rer rei rea res ree rec]
  	(rept (parseres (parsejw rer rei rea res ree rec))))
  (route/not-found "Not Found"))