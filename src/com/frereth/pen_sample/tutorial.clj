(ns com.frereth.pen-sample.tutorial
  (:use [penumbra.opengl])
  (:require [penumbra.app :as app]))

(defn init [state]
  (app/vsync! true)
  state)

(defn reshape [[x y w h] state]
  (comment (frustum-view 60.0 (/ (double w) h) 1.0 100.0)
           (load-identity))
  (let [aspect (/ (float w) h)
        height (if (> 1 aspect) (/ 1.0 aspect) 1)
        aspect (max 1 aspect)]
    (ortho-view (- aspect) aspect (- height) height -1 1)
    state))

(defn mouse-drag [[dx dy] [x y] button state]
  (assoc state
    :rot-x (+ (:rot-x state) dy)
    :rot-y (+ (:rot-y state) dx)))

(defn update [[delta time] state]
  (println "Updating")
  (update-in state [:rot-y]
             #(rem (+ % (* 90 delta)) 360)))

(defn draw-clock [hour minute second]
  (push-matrix
   (scale 0.5 0.5 1)
   (rotate 180 0 0 1)
   (push-matrix
    (rotate (* -30 (rem hour 24)) 0 0 1)
    (color 1 1 1)
    (line-width 5)
    (draw-lines (vertex 0 0) (vertex 0 -0.5)))
   (push-matrix
    (rotate (* -6 (rem minute 60)) 0 0 1)
    (color 1 1 1)
    (line-width 2)
    (draw-lines (vertex 0 0) (vertex 0 -1)))
   (push-matrix
    (rotate (* -6 (rem second 60)) 0 0 1)
    (color 1 0 0)
    (line-width 1)
    (draw-lines (vertex 0 0) (vertex 0 1)))))

(defn display [[delta time] state]
  (println "Drawing")
  ;; It seems like a very interesting tidbit that this next call..
  (comment) (draw-clock (/ time 3600) (/ time 60) time)

  ;; ...keeps this triangle from being drawn
  (translate 0 -0.93 -3)
  (rotate (:rot-x state) 1 0 0)
  (rotate (:rot-y state) 0 1 0)
  (draw-triangles
   (color 1 0 0) (vertex 1 0)
   (color 0 1 0) (vertex -1 0)
   (color 0 0 1) (vertex 0 1.86))
  (app/repaint!))

(defn run []
  (app/start
   {:display display
    :reshape reshape
    :mouse-drag mouse-drag
    :init init
    :update update}
   {:rot-x 0 :rot-y 0}))
