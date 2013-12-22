(defproject com.frereth/visuals "0.1.0-SNAPSHOT"
  :description "Really just dinking around trying to figure out how Penumbra works"
  :url "TODO"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [;; Yes, this next line should tell you how ridiculously
                 ;; experimental this project is.
                 [jimrthy/penumbra "0.6.6-SNAPSHOT"]
                 [kephale/cantor "0.4.1"]
  		 [kephale/lwjgl "2.9.0"]
                 [kephale/lwjgl-util "2.9.0"]
                 [kephale/lwjgl-natives "2.9.0"]
                 [org.clojure/math.combinatorics "0.0.2"]
                 [org.clojure/clojure "1.5.1"]
                 [slick-util "1.0.0"]]

  ;; I suspect that I may want this next option.
  ;; TODO: What does it actually mean?
  :pedantic? :abort
  ;; Important:
  ;; run lein git-deps to actually download this!
  ;;:git-dependencies [["git@github.com:jimrthy/penumbra.git"]]
  ;;:source-paths ["src" ".lein-git-deps/penumbra/src/"]
  ;;:java-source-paths [".lein-git-deps/penumbra/java/"]
  ;;:plugins [[lein-git-deps "0.0.1-SNAPSHOT"]]
  :profiles {:dev {:dependencies [[org.clojure/tools.namespace "0.2.4"]]
                   :source-paths ["dev"]}}
  :repl-options {:init-ns user}
  :main com.frereth.visuals)
