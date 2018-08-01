# funjible-test-project

This project was created for these purposes:

+ run performance tests on the code in the
  [funjible](https://github.com/jafingerhut/funjible) library,
  currently in the namespace `funjible-test-project.perf-test`.

+ It was used to test prototypes of some of the functions that became
  part of the `funjible.set-with-patching` namespace.


## Usage

Run `lein run help` for help on running performance tests.


### Observation about long-running performance benchmarks using `criterium` in a VM that you suspend and later resume

Note: It uses the
[`criterium`](https://github.com/hugoduncan/criterium) to measure the
run time of several of the functions in `clojure.set` and
`funjible.set` namespaces with different size sets as arguments.

I was running some of these performance tests in a Linux virtual
machine with the stack of software below, and was curious whether
suspending and saving such a VM's state that was in the middle of one
of these long-running sequences of performance measurements, then
restoring it later, would throw off the performance results a lot,
because of the change in system time that occurs on restoring the VM.

+ 2015 model MacBook Pro
+ OSX 10.12.6 host OS
+ VirtualBox 5.2.16 r123759
+ Ubuntu 18.04.1 guest OS
+ Oracle JDK 1.8.0_181

(I am recording all of these pieces of the system for completeness
sake.  I suspect that the behavior described below would be true for
other OS, virtualization software, and JDK combinations, too, but
haven't tested other combinations.)

It turns out that `criterium` uses Java's
[`nanoTime`](https://docs.oracle.com/javase/8/docs/api/java/lang/System.html#nanoTime--)
method, called just before and just after a sequence of function calls
that are being measured for run time, to determine the elapsed time
across that sequence of calls.  I tested in such a VM with a Clojure
REPL, calling `(System/nanoTime)` at one time shortly before
suspending and saving the VM, then waiting about 2 minutes before
restoring the VM to a running state.  I ran `(System/nanoTime)` again
shortly after restoring the VM, and subtracted the after-restoring and
before-suspending results, and compared it to the wall clock time of
the host machine that had elapsed between the two calls.  The
difference did _not_ include the time that the VM was suspended, only
the time that it was running.

So it seems to be that suspending such a VM during a long-running
collection of performance results should not throw them off, or at
least not nearly as much as you might expect if the elapsed time was
calculated by `criterium` by using the system time and date, which
_does_ have a sudden jump from just before suspending the VM, to
shortly after restoring it.


## Sample interaction for testing "patching" behavior

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

Copyright © 2018 Andy Fingerhut

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
