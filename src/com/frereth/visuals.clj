(ns com.frereth.visuals
  (:require [penumbra.opengl :as gl]
            [penumbra.app :as app]))

(defn reshape [[x y w h] state]
  (frustum-view 60.0 (/ (double width) height) 1.0 100.0)
  (load-identity)
  state)

(defn display [[delta time] state]
  (translate 0 -0.93 -3)
  (draw-triangles
   (color 1 0 0) (vertex 1 0)
   (color 0 1 0) (vertex -1 0)
   (color 0 0 1) (vertex 0 1.86)))

(defn main []
  (app/start
   {:display display :reshape reshape}
   {}))


