(ns holland.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [holland.routes.routes :refer :all]))

(def app
  (wrap-defaults app-routes site-defaults))
