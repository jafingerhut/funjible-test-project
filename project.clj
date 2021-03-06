(defproject funjible-test-project "0.1.0"
  :description "Test project for funjible library"
  :url "http://github.com/jafingerhut/funjible-test-project"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [funjible "1.0.0"]
                 [org.clojure/data.avl "0.0.17"]
                 [org.clojure/data.int-map "0.2.4"]
                 ;;[org.clojure/data.priority-map "0.0.10"]
                 ;;[org.flatland/useful "0.11.5"]
                 ;;[org.clojure/tools.trace "0.7.6"]
                 [criterium "0.4.2"]]
  ;;:profiles {:dev {:dependencies [[funjible "0.2.0"]]}}
  ;; The default JVM command line options used by Leiningen, if you do
  ;; not have a `:jvm-opts` value like the one below, lead to much
  ;; lower performance, because they disable some or most JIT
  ;; optimizations, with the hope of achieving faster startup times.
  :jvm-opts ^:replace ["-server" "-Xmx1024m"]
  :main funjible-test-project.perf-test)
