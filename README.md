# funjible-test-project

A Clojure library designed to ... well, that part is up to you.

## Usage

FIXME


## Sample interaction

```clojure
(require '[funjible-test-project.core :as c])
(c/foo0 1)
(require '[funjible-test-project.foo1 :as foo1])
(foo1/foo1 5)
(require '[clojure.data :as d])
(d/diff #{1 3 5} #{2 3})
(require '[funjible-test-project.set :as s])
(c/foo0 1)
(foo1/foo1 5)
(d/diff #{1 3 5} #{2 3})
(require '[clojure.set :as cset])
(cset/difference #{5 7} #{3 5})
(cset/difference #{5 7})
(cset/difference #{5 7} #{3 5} #{1 2})
(doc cset/difference)
(source cset/difference)
(pprint (meta #'clojure.set/difference))
```


Output from sample interaction, interspersed with a few notes in
comments.

```clojure

;; The namespace funjible-test-project.set has not been loaded yet,
;; and will not be until it is explicitly require'd below.  Until
;; then, clojure.set/difference has its original value.

user=> (require '[funjible-test-project.core :as c])
nil
user=> (c/foo0 1)
funjible-test-project.core/foo0 calling (clojure.set/difference #{1 2} #{x}) -> #{2}
nil
user=> (require '[funjible-test-project.foo1 :as foo1])
nil
user=> (foo1/foo1 5)
funjible-test-project.foo1/foo1 calling (clojure.set/difference #{1 2} #{x}) -> #{1 2}
nil
user=> (require '[clojure.data :as d])
nil
user=> (d/diff #{1 3 5} #{2 3})
[#{1 5} #{2} #{3}]

;; As a side effect of this require, not only does the function
;; funjible-test-project.set/difference become defined, but the value
;; of that function is used to replace the original definition of
;; clojure.set/difference.

user=> (require '[funjible-test-project.set :as s])
nil

;; Thus, this same function call as above now behaves differently.

user=> (c/foo0 1)
Called 2-arg version of funjible-test-project.set/difference
funjible-test-project.core/foo0 calling (clojure.set/difference #{1 2} #{x}) -> #{2}
nil

;; As does this one, which also calls clojure.set/difference.

user=> (foo1/foo1 5)
Called 2-arg version of funjible-test-project.set/difference
funjible-test-project.foo1/foo1 calling (clojure.set/difference #{1 2} #{x}) -> #{1 2}
nil

;; clojure.data/diff calls clojure.set/difference when it is used to
;; compare sets that are different from each other.  Note that the new
;; version of clojure.set/difference is _not_ being used here, which
;; we can tell because there is no output about which version of
;; funjible-test-project.set/difference is being called.  This is
;; because Clojure is compiled with direct linking enabled, so
;; changing the value of the var #'clojure.set/difference has no
;; effect on which function clojure.data/diff calls.

user=> (d/diff #{1 3 5} #{2 3})
[#{1 5} #{2} #{3}]

;; Even if we now require clojure.set and call clojure.set/difference,
;; its value is different.

user=> (require '[clojure.set :as cset])
nil
user=> (cset/difference #{5 7} #{3 5})
Called 2-arg version of funjible-test-project.set/difference
#{7}
user=> (cset/difference #{5 7})
Called 1-arg version of funjible-test-project.set/difference
#{7 5}
user=> (cset/difference #{5 7} #{3 5} #{1 2})
Called 3+-arg version of funjible-test-project.set/difference
Called 2-arg version of funjible-test-project.set/difference
Called 2-arg version of funjible-test-project.set/difference
#{7}
user=> (doc cset/difference)
-------------------------
clojure.set/difference
([s1] [s1 s2] [s1 s2 & sets])
  Not really a better version of clojure.set/difference.  Actually
  worse, in that it behaves just like clojure.set/difference from
  Clojure 1.9.0, but it also prints a message to *out* for debugging
  purposes.

  Return a set that is the first set without elements of the remaining
  sets
nil
user=> (source cset/difference)
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
nil
user=> (pprint (meta #'clojure.set/difference))
{:arglists ([s1] [s1 s2] [s1 s2 & sets]),
 :doc
 "Not really a better version of clojure.set/difference.  Actually\n  worse, in that it behaves just like clojure.set/difference from\n  Clojure 1.9.0, but it also prints a message to *out* for debugging\n  purposes.\n\n  Return a set that is the first set without elements of the remaining\n  sets",
 :added "1.0",
 :line 8,
 :column 1,
 :file "funjible_test_project/set.clj",
 :name difference,
 :ns #object[clojure.lang.Namespace 0x1d71e50d "clojure.set"]}
nil
```

## License

Copyright © 2018 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
