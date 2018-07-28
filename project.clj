(defproject funjible-test-project "0.1.0"
  :description "Test project for funjible library"
  :url "http://github.com/jafingerhut/funjible-test-project"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]]
  ;;:profiles {:dev {:dependencies [[funjible "0.2.0"]]}}
  :jvm-opts ^:replace ["-server" "-Xmx1024m"])
