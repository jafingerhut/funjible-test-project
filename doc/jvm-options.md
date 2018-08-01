# JVM command line options


All of the results below are for this version of Leiningen and the
JVM:

```bash
$ lein version
Leiningen 2.7.1 on Java 1.8.0_65 Java HotSpot(TM) 64-Bit Server VM
```

With this line in the `project.clj` file:

```clojure
  :jvm-opts ^:replace ["-server" "-Xmx1024m"]
```

Running the command `lein run short-funjible.set-union-perf` showed
`java` processes that had been started with these command line
options:

```bash
$ ps axguwww|grep java
andy            23800 171.3  4.2  4936372 174344 s000  R+    4:27PM   1:02.80 /usr/bin/java -classpath /Users/andy/clj/funjible-test-project/test:/Users/andy/clj/funjible-test-project/src:/Users/andy/clj/funjible-test-project/dev-resources:/Users/andy/clj/funjible-test-project/resources:/Users/andy/clj/funjible-test-project/target/classes:/Users/andy/.m2/repository/org/clojure/clojure/1.9.0/clojure-1.9.0.jar:/Users/andy/.m2/repository/org/clojure/spec.alpha/0.1.143/spec.alpha-0.1.143.jar:/Users/andy/.m2/repository/org/clojure/core.specs.alpha/0.1.24/core.specs.alpha-0.1.24.jar:/Users/andy/.m2/repository/funjible/funjible/0.2.0-SNAPSHOT/funjible-0.2.0-SNAPSHOT.jar:/Users/andy/.m2/repository/org/clojure/data.avl/0.0.17/data.avl-0.0.17.jar:/Users/andy/.m2/repository/org/clojure/data.int-map/0.2.4/data.int-map-0.2.4.jar:/Users/andy/.m2/repository/criterium/criterium/0.4.2/criterium-0.4.2.jar:/Users/andy/.m2/repository/org/clojure/tools.nrepl/0.2.12/tools.nrepl-0.2.12.jar:/Users/andy/.m2/repository/clojure-complete/clojure-complete/0.2.4/clojure-complete-0.2.4.jar -Dfile.encoding=UTF-8 -server -Xmx1024m -Dclojure.compile.path=/Users/andy/clj/funjible-test-project/target/classes -Dfunjible-test-project.version=0.1.0 -Dclojure.debug=false clojure.main -i /private/var/folders/s0/p1l04yx95n959y_1c4z5r2jw0000gn/T/form-init3729437590316519221.clj
andy            23959   0.0  0.0  2432792    556 s001  R+    4:27PM   0:00.00 grep --color=auto java
andy            23798   0.0  2.5  4927972 103416 s000  S+    4:26PM   0:03.50 /usr/bin/java -Xbootclasspath/a:/Users/andy/.lein/self-installs/leiningen-2.7.1-standalone.jar -Dfile.encoding=UTF-8 -Dmaven.wagon.http.ssl.easy=false -Dmaven.wagon.rto=10000 -XX:+TieredCompilation -XX:TieredStopAtLevel=1 -Dleiningen.original.pwd=/Users/andy/clj/funjible-test-project -Dleiningen.script=/Users/andy/.dotfiles/bin/lein-2.7.1 -classpath /Users/andy/.lein/self-installs/leiningen-2.7.1-standalone.jar clojure.main -m leiningen.core.main run short-funjible.set-union-perf
```

The last line is for the JVM process started first by Leiningen, which
then starts another JVM, which is shown in the first output line
above.

```
/usr/bin/java
    -classpath
    /Users/andy/clj/funjible-test-project/test:
        /Users/andy/clj/funjible-test-project/src:
        /Users/andy/clj/funjible-test-project/dev-resources:
        /Users/andy/clj/funjible-test-project/resources:
        /Users/andy/clj/funjible-test-project/target/classes:
        /Users/andy/.m2/repository/org/clojure/clojure/1.9.0/clojure-1.9.0.jar:
        /Users/andy/.m2/repository/org/clojure/spec.alpha/0.1.143/spec.alpha-0.1.143.jar:
        /Users/andy/.m2/repository/org/clojure/core.specs.alpha/0.1.24/core.specs.alpha-0.1.24.jar:
        /Users/andy/.m2/repository/funjible/funjible/0.2.0-SNAPSHOT/funjible-0.2.0-SNAPSHOT.jar:
        /Users/andy/.m2/repository/org/clojure/data.avl/0.0.17/data.avl-0.0.17.jar:
        /Users/andy/.m2/repository/org/clojure/data.int-map/0.2.4/data.int-map-0.2.4.jar:
        /Users/andy/.m2/repository/criterium/criterium/0.4.2/criterium-0.4.2.jar:
        /Users/andy/.m2/repository/org/clojure/tools.nrepl/0.2.12/tools.nrepl-0.2.12.jar:
        /Users/andy/.m2/repository/clojure-complete/clojure-complete/0.2.4/clojure-complete-0.2.4.jar
    -Dfile.encoding=UTF-8
    -server
    -Xmx1024m
    -Dclojure.compile.path=/Users/andy/clj/funjible-test-project/target/classes
    -Dfunjible-test-project.version=0.1.0
    -Dclojure.debug=false
    clojure.main
    -i
    /private/var/folders/s0/p1l04yx95n959y_1c4z5r2jw0000gn/T/form-init3729437590316519221.clj
```

With the `:jvm-opts` key defined as shown above, the following command
line options are given to the JVM:

```
    -server
    -Xmx1024m
```

With the `:jvm-opts` line of the `project.clj` file commented out,
here are the command lines used:

```bash
$ ps axguwww|grep java
andy            24272 121.8  3.4  4924056 143472 s000  R+    4:34PM   0:38.57 /usr/bin/java -classpath /Users/andy/clj/funjible-test-project/test:/Users/andy/clj/funjible-test-project/src:/Users/andy/clj/funjible-test-project/dev-resources:/Users/andy/clj/funjible-test-project/resources:/Users/andy/clj/funjible-test-project/target/classes:/Users/andy/.m2/repository/org/clojure/clojure/1.9.0/clojure-1.9.0.jar:/Users/andy/.m2/repository/org/clojure/spec.alpha/0.1.143/spec.alpha-0.1.143.jar:/Users/andy/.m2/repository/org/clojure/core.specs.alpha/0.1.24/core.specs.alpha-0.1.24.jar:/Users/andy/.m2/repository/funjible/funjible/0.2.0-SNAPSHOT/funjible-0.2.0-SNAPSHOT.jar:/Users/andy/.m2/repository/org/clojure/data.avl/0.0.17/data.avl-0.0.17.jar:/Users/andy/.m2/repository/org/clojure/data.int-map/0.2.4/data.int-map-0.2.4.jar:/Users/andy/.m2/repository/criterium/criterium/0.4.2/criterium-0.4.2.jar:/Users/andy/.m2/repository/org/clojure/tools.nrepl/0.2.12/tools.nrepl-0.2.12.jar:/Users/andy/.m2/repository/clojure-complete/clojure-complete/0.2.4/clojure-complete-0.2.4.jar -Dfile.encoding=UTF-8 -XX:+TieredCompilation -XX:TieredStopAtLevel=1 -XX:-OmitStackTraceInFastThrow -Dclojure.compile.path=/Users/andy/clj/funjible-test-project/target/classes -Dfunjible-test-project.version=0.1.0 -Dclojure.debug=false clojure.main -i /private/var/folders/s0/p1l04yx95n959y_1c4z5r2jw0000gn/T/form-init6887133283531060876.clj
andy            24274   0.0  0.0  2423380    308 s001  R+    4:35PM   0:00.00 grep --color=auto java
andy            24269   0.0  2.5  4927972 103400 s000  S+    4:34PM   0:03.46 /usr/bin/java -Xbootclasspath/a:/Users/andy/.lein/self-installs/leiningen-2.7.1-standalone.jar -Dfile.encoding=UTF-8 -Dmaven.wagon.http.ssl.easy=false -Dmaven.wagon.rto=10000 -XX:+TieredCompilation -XX:TieredStopAtLevel=1 -Dleiningen.original.pwd=/Users/andy/clj/funjible-test-project -Dleiningen.script=/Users/andy/.dotfiles/bin/lein-2.7.1 -classpath /Users/andy/.lein/self-installs/leiningen-2.7.1-standalone.jar clojure.main -m leiningen.core.main run short-funjible.set-union-perf
```

Here is the first of those JVM command lines broken up over multiple
lines:

```
/usr/bin/java
    -classpath
    /Users/andy/clj/funjible-test-project/test:
        /Users/andy/clj/funjible-test-project/src:
        /Users/andy/clj/funjible-test-project/dev-resources:
        /Users/andy/clj/funjible-test-project/resources:
        /Users/andy/clj/funjible-test-project/target/classes:
        /Users/andy/.m2/repository/org/clojure/clojure/1.9.0/clojure-1.9.0.jar:
        /Users/andy/.m2/repository/org/clojure/spec.alpha/0.1.143/spec.alpha-0.1.143.jar:
        /Users/andy/.m2/repository/org/clojure/core.specs.alpha/0.1.24/core.specs.alpha-0.1.24.jar:
        /Users/andy/.m2/repository/funjible/funjible/0.2.0-SNAPSHOT/funjible-0.2.0-SNAPSHOT.jar:
        /Users/andy/.m2/repository/org/clojure/data.avl/0.0.17/data.avl-0.0.17.jar:
        /Users/andy/.m2/repository/org/clojure/data.int-map/0.2.4/data.int-map-0.2.4.jar:
        /Users/andy/.m2/repository/criterium/criterium/0.4.2/criterium-0.4.2.jar:
        /Users/andy/.m2/repository/org/clojure/tools.nrepl/0.2.12/tools.nrepl-0.2.12.jar:
        /Users/andy/.m2/repository/clojure-complete/clojure-complete/0.2.4/clojure-complete-0.2.4.jar
    -Dfile.encoding=UTF-8
    -XX:+TieredCompilation
    -XX:TieredStopAtLevel=1
    -XX:-OmitStackTraceInFastThrow
    -Dclojure.compile.path=/Users/andy/clj/funjible-test-project/target/classes
    -Dfunjible-test-project.version=0.1.0
    -Dclojure.debug=false
    clojure.main
    -i
    /private/var/folders/s0/p1l04yx95n959y_1c4z5r2jw0000gn/T/form-init6887133283531060876.clj
```

By putting those two different java command lines into separate files
and using `diff` to compare them, one can see that the main
differences are that without the `:jvm-opts` key in the Leiningen
`project.clj` file, these command line options are given to the JVM:

```
    -XX:+TieredCompilation
    -XX:TieredStopAtLevel=1
    -XX:-OmitStackTraceInFastThrow
```

I also see the following warning printed in the output of the `lein
run short-funjible.set-union-perf` command:

```
WARNING: JVM argument TieredStopAtLevel=1 is active, and may lead to unexpected results as JIT C2 compiler may not be active. See http://www.slideshare.net/CharlesNutter/javaone-2012-jvm-jit-for-dummies.
```

The warning above is printed by the Criterium library's `benchmark`
macro, when it detects that certain command line options that can
hinder JIT optimization are present.

The default Leiningen version takes about twice as long to run several
of the benchmarks as the one with this line in the `project.clj` file:

```clojure
  :jvm-opts ^:replace ["-server" "-Xmx1024m"]
```
