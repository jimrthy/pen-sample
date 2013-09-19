(defproject com.frereth/visuals "0.1.0-SNAPSHOT"
  :description "TODO"
  :url "TODO"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [slick-util "1.0.0"]
                 [kephale/cantor "0.4.1"]
                 [org.clojure/math.combinatorics "0.0.2"]
                 [kephale/lwjgl "2.9.0"]
                 [kephale/lwjgl-util "2.9.0"]
                 [kephale/lwjgl-natives "2.9.0"]]
  ;; Important:
  ;; run lein git-deps to actually download this!
  :git-dependencies [["git@github.com:jimrthy/penumbra.git"]]
  :source-paths ["src" ".lein-git-deps/penumbra/src/"]
  :plugins [[lein-git-deps "0.0.1-SNAPSHOT"]]
  :profiles {:dev {:dependencies [[org.clojure/tools.namespace "0.2.4"]]
                   :source-paths ["dev"]}}
  :main com.frereth.visuals/main)
