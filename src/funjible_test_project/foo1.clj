(ns funjible-test-project.foo1
  (:require [clojure.set :as set]))

(defn foo1
  "funjible-test-project.foo1/foo1 doc string"
  [x]
  (println "funjible-test-project.foo1/foo1 calling (clojure.set/difference #{1 2} #{x}) ->"
           (set/difference #{1 2} #{x})))
