(ns funjible-test-project.set
  ;; This namespace does not 'use' anything from clojure.set, but it
  ;; does alter the value of Vars in that namespace, so we want to
  ;; ensure that it has been loaded before that happens.
  (:require [clojure.set :as set]))


(defn difference
  "Not really a better version of clojure.set/difference.  Actually
  worse, in that it behaves just like clojure.set/difference from
  Clojure 1.9.0, but it also prints a message to *out* for debugging
  purposes.

  Return a set that is the first set without elements of the remaining
  sets"
  {:added "1.never"}
  ([s1]
     {:pre [(set? s1)]}
     (println "Called 1-arg version of funjible-test-project.set/difference")
     s1)
  ([s1 s2] 
     {:pre [(set? s1) (set? s2)]}
     (println "Called 2-arg version of funjible-test-project.set/difference")
     (if (< (count s1) (count s2))
       (reduce (fn [result item] 
                   (if (contains? s2 item) 
                     (disj result item) 
                     result))
               s1 s1)
       (reduce disj s1 s2)))
  ([s1 s2 & sets] 
     ;; No need for preconditions here, because the precondition in
     ;; the 2-argument version will catch any non-set arguments to
     ;; this version.
     (println "Called 3+-arg version of funjible-test-project.set/difference")
     (reduce difference s1 (conj sets s2))))


;; Replace clojure.set/difference with the version of difference
;; above.

(defn- update-var-val-and-meta
  "Given a Var orig-var and another new-var, replace the value of
  orig-var with that of new-var, using alter-var-root.  Afterwards,
  any calls made to the function that is the value of orig-var will
  behave the same as calls to new-var (unless they were compiled with
  direct linking enabled, in which case they will still use the
  original definition).

  Also replace the values of metadata keys :doc :file :line
  and :column of orig-var with the corresponding values of those keys
  from var new-var, using alter-meta!.  Afterwards, and any uses of
  clojure.repl/doc or clojure.repl/source on orig-var will see the new
  definition, not the original one."
  [orig-var new-var]
  (alter-var-root orig-var (constantly (deref new-var)))
  (alter-meta! orig-var merge (select-keys (meta new-var)
                                           [:doc :file :line :column])))

(update-var-val-and-meta #'clojure.set/difference #'difference)
