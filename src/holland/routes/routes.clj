(ns holland.routes.routes
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [net.cgrand.enlive-html :as html]
            [net.cgrand.enlive-html :refer [deftemplate defsnippet]]
            [noir.session :as session]
            [noir.response :as resp]
            [holland.db :as db]))

;;helper functions


(defn cuuid [] (str (java.util.UUID/randomUUID)))

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

(deftemplate layout "public/layout.html"
  [content]
  [:div.content] (html/substitute content))

(defsnippet home "public/index.html"
  [:div.content]
  []
  [:part2] (html/substitute (part2))
  [:part3] (html/substitute (part3)))

(defn homepage []
  (layout (home)))

(defsnippet logins "public/login.html"
  [:div.content]
  [mes]
  [:div.panel-message] (html/content mes))

(defn loginpage [& mes]
  (layout (logins (first mes))))

(defsnippet regs "public/register.html"
  [:div.content]
  [mes]
  [:div.panel-message] (html/content mes))

(defn registerpage [& mes]
  (layout (regs (first mes))))

(defsnippet intros "public/intro.html"
  [:div.content]
  [])

(defn intropage []
  (layout (intros)))

;;Template processing functions
(defn val-registration [nm age sex pendidikan jurusan email phone kode keterangan username pass1 pass2]
  (if (= pass1 pass2)
    (if (= 0 (count (db/login-f username)))
      (let [nuuid (cuuid)]
        (do
        (db/addprofdb nm age sex pendidikan jurusan email phone kode keterangan nuuid)
        (db/adduser username pass1 nuuid)
        (loginpage "Anda telah terdaftar")))
      (registerpage "Username sudah ada, gunakan yang lain."))
    (registerpage "Password doesn't match.")))

(defn validate [y n]
  (if (session/get :username)
    y
    n))

;;routes

(defroutes app-routes
  (GET "/" [] 
    (validate (intropage) (loginpage)))
  (GET "/test" [] 
    (validate (homepage) (loginpage)))
  (POST "/login-action" {params :params}
    (let [username (:username params)
          password (:password params)]
        (if (not= 0 (count (db/login-f username)))
          (if (= password (apply :password (db/login-f username)))
            (do
              (session/clear!)
              (session/put! :username username)
              (intropage))
            (loginpage "Wrong password or the username doesn't exist"))
          (loginpage "Wrong password or the username doesn't exist"))))
  (GET "/register" []
    (registerpage))
  (POST "/register-action" {params :params}
    (val-registration       
      (:nama params) 
      (Integer/parseInt (:umur params)) 
      (:gender params) 
      (:sekolah params) 
      (:jurusan params) 
      (:email params) 
      (Integer/parseInt (:phone params)) 
      (str (:kode params)) 
      (:keterangan params) 
      (:username params) 
      (:password params)
      (:password2 params)))
  ;nm age sex pendidikan jurusan email phone kode keterangan username pass1 pass2
  (GET "/res/:rer/:rei/:rea/:res/:ree/:rec" [rer rei rea res ree rec]
  	(do
      (session/clear!)
      (rept (parseres (parsejw rer rei rea res ree rec)))))
  (GET "/logout" []
    (do
      (session/clear!)
      (resp/redirect "/")))
  (route/not-found "Not Found"))