(ns com.frereth.visuals
  (:require [penumbra.opengl :as gl]
            [penumbra.app :as app]))

(defn reshape [[x y w h] state]
  (gl/frustum-view 60.0 (/ (double w) h) 1.0 100.0)
  (gl/load-identity)
  state)

(defn display [[delta time] state]
  (gl/translate 0 -0.93 -3)
  (gl/draw-triangles
   (gl/color 1 0 0) (gl/vertex 1 0)
   (gl/color 0 1 0) (gl/vertex -1 0)
   (gl/color 0 0 1) (gl/vertex 0 1.86)))

(defn main []
  (app/start
   {:display display :reshape reshape}
   {}))


