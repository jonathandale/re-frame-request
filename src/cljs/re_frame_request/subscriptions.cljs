(ns re-frame-request.subscriptions
  (:require [re-frame.core :refer [reg-sub subscribe]]))

(defn register-subscriptions
  "Register re-frame-request subscriptions"
  []

  (reg-sub
   :request/core
   (fn [{:keys [request]} _] request))

  (reg-sub
   :request/track-request
   (fn [_ _] (subscribe [:request/core]))
   (fn [request [_ request-name]]
     (get request request-name {:status :never-requested})))

  (reg-sub
   :request/is-dispatching
   (fn [_ [_ request-name]] (subscribe [:request/core]))
   (fn [request [_ request-name]]
     (= :loading (get-in request [request-name :status])))))
