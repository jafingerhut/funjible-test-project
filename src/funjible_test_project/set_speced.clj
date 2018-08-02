(ns funjible-test-project.set-speced
  (:require [clojure.set :as set]
            [clojure.spec.alpha :as s]
            [clojure.spec.test.alpha :as stest]))

(s/fdef set/subset?
  :args (s/cat :s1 set? :s2 set?))

(s/fdef set/superset?
  :args (s/cat :s1 set? :s2 set?))

(s/fdef set/union
  :args (s/* set?))

(s/fdef set/intersection
  :args (s/+ set?))

(s/fdef set/difference
  :args (s/+ set?))

(defn instrument-clojure-set-fns []
  (doseq [sym `[set/subset? `set/superset?
                `set/union `set/intersection `set/difference]]
    (stest/instrument sym)))

;;  (stest/instrument `set/subset?)
;;  (stest/instrument `set/superset?)
;;  (stest/instrument `set/union)
;;  (stest/instrument `set/intersection)
;;  (stest/instrument `set/difference))
