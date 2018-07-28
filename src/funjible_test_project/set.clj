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

;; The alter-var-root call below is enough to replace the behavior of
;; clojure.set/difference with that of
;; funjible-test-project.set/difference.  However, it leaves the
;; output of the following forms unchanged:

;; (doc clojure.set/difference)
;; (source clojure.set/difference)
;; (meta #'clojure.set/difference)

(alter-var-root (var clojure.set/difference) (constantly difference))

;; The alter-meta! call below causes the output of the doc, source,
;; and meta calls above to change to correspond to those of
;; funjible-test-project.set/difference.  Doing this will be better
;; for the sanity of anyone using the output of those calls to
;; investigate the behavior of the modified clojure.set/difference.

(alter-meta! (var clojure.set/difference)
             merge (select-keys (meta #'difference)
                                [:doc :file :line :column]))
