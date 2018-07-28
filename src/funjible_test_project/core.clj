(ns funjible-test-project.core
  (:require [clojure.set :as set]
            [funjible-test-project.foo1 :as foo1]))

(defn foo0
  "funjible-test-project.core/foo0 doc string"
  [x]
  (println "funjible-test-project.core/foo0 calling (clojure.set/difference #{1 2} #{x}) ->"
           (set/difference #{1 2} #{x})))
