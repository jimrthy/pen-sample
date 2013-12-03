(ns com.frereth.visuals
  (:require [penumbra.opengl :as gl]
            [penumbra.app :as app]))

(defn init [state]
  (println "Initialized")
  ;; Don't want to do this
  (gl/frustum-view 60.0 (/ (double 4) 3) 1.0 100.0)
  (gl/load-identity)  

  state)

(defn reshape [[x y w h] state]
  (println "Reshaping to: " x y w h)
  ;; Frustrating. This is never happening. What gives?
  (throw (RuntimeException. "Got here!"))
  (comment
    (gl/frustum-view 60.0 (/ (double w) h) 1.0 100.0)
    (gl/load-identity))
  (let [aspect (/ (float w) h)
        height (if (> 1 aspect) (/ 1.0 aspect) 1)
        aspect (max 1 aspect)]
    (gl/ortho-view (- aspect) aspect (- height) height -1 1)
    state))

(defn update [[delta time] state]
  (into state {:frame-count (inc (:frame-count state))}))

(defn display [[delta time] state]
  (let [frame (:frame-count state)]
    (comment (println "Draw Frame" frame))
    (gl/translate 0 -0.93 -3)
    (gl/draw-triangles
     (gl/color 1 0 0) (gl/vertex 1 0)
     (gl/color 0 1 0) (gl/vertex -1 0)
     (gl/color 0 0 1) (gl/vertex 0 1.86)))

  (app/repaint!))

(defn -main []
  (app/start
   {:display display :reshape reshape :update update :init init}
   ;; TODO: It looks like the State I'm passing in needs to be
   ;; an Atom.
   ;; Which seems horribly wrong.
   ;; penumbra.app converts this to an atom in its create function.
   ;; I have to assume that there was a good reason for this decision.
   {:frame-count 0 :fluid true}))


