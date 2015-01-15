(ns com.frereth.pen-sample.check-lwjgl3
  (:import [java.nio ByteBuffer IntBuffer]
           [org.lwjgl Sys]
           [org.lwjgl.glfw
            Callbacks
            GLFW
            GLFWCursorEnterCallback
            GLFWCursorPosCallback
            GLFWErrorCallback
            GLFWKeyCallback
            GLFWMouseButtonCallback
            GLFWScrollCallback
            GLFWvidmode]
           [org.lwjgl.opengl GL11 GLContext]
           [org.lwjgl.system MemoryUtil])
  (:gen-class))

(def gl-true GL11/GL_TRUE)
(def gl-false GL11/GL_FALSE)
(def fps-60 (/ 1000 60.0))  ; ms to achieve that framerate

(defn event-loop
  [hwnd]
  (GLFW/glfwMakeContextCurrent hwnd)
  ;; Specifically called out as critical. It's needed for interop w/
  ;; any context that's externally managed - maybe esp. GLFW's.
  ;; LWJGL detects the context that is current in the current thread,
  ;; creates the ContextCapabilities instance, and makes the OpenGL
  ;; bindings available for use
  (try
    ;; This is throwing an illegal state exception because
    ;; there's no current Display.
    ;; Without it, though, there's no current Context
    (GLContext/createFromCurrent)
    (catch IllegalStateException ex
      (let [msg (.getMessage ex)]
        (println "Creating Context from the Current one failed:\n" msg)
        (throw (ex-info "Context Creation" {})))))
  ;; Set the clear color
  ;; This is the line that fails if I don't set the context above
  (GL11/glClearColor 1.0 0.0 0.0 0.0)

  ;; Run the rendering loop until the user has attempted to close the
  ;; window or has pressed the ESCAPE key
  (loop [n 0
         previous (GLFW/glfwGetTime)]
    (when-not (= (GLFW/glfwWindowShouldClose hwnd) gl-true)
      (GL11/glClear (bit-or GL11/GL_COLOR_BUFFER_BIT GL11/GL_DEPTH_BUFFER_BIT))

      ;; TODO: Draw the frame

      (GLFW/glfwSwapBuffers hwnd)

      ;; Poll for window events. The key callback gets invoked here
      (GLFW/glfwPollEvents)

      ;; Shoot for 60 fps
      (let [now (GLFW/glfwGetTime)
            delta (- now previous)
            pause (- fps-60 delta)]
        ;; Don't need this with vsync enabled
        ;; At least, in theory.
        ;; Actually, for physics simulation, we
        ;; really need a fixed timestep that
        ;; does linear interpolation from one frame
        ;; to the next.
        (when (< 0 pause)
          (Thread/sleep pause))
        (when (= 0 (mod n 600))
          (println n))
        (recur (inc n) (GLFW/glfwGetTime)))))
  (println "Exiting Event Loop")
  (GLFW/glfwDestroyWindow hwnd))

(defn keyboard-callback
  []
  (proxy
      [GLFWKeyCallback]
      []
      (invoke [^Long hwnd ^Long key ^Long scan-code ^Long action ^Long mods]
        (println "Key " key " did " action)
        (when (and (= key GLFW/GLFW_KEY_ESCAPE)
                   (= action GLFW/GLFW_RELEASE))
          ;; This change gets detected in the rendering loop
          ;; It's very tempting to set this flag in something
          ;; like an atom, but that really violates DRY.
          ;; I think.
          (GLFW/glfwSetWindowShouldClose hwnd gl-true)))))

(defn mouse-button-callback
  []
  (proxy [GLFWMouseButtonCallback] []
      (invoke [^Long hwnd ^Long button ^Long action ^Long mods]
        (println "Mouse Button: " (condp = button
                                    GLFW/GLFW_MOUSE_BUTTON_1 "1"
                                    GLFW/GLFW_MOUSE_BUTTON_2 "2"
                                    GLFW/GLFW_MOUSE_BUTTON_3 "3")
                 "\nAction: " (if (= action GLFW/GLFW_PRESS)
                                "Pressed"
                                "Released")))))

(defn mouse-position-callback
  []
  (proxy [GLFWCursorPosCallback] []
    (invoke [^Long hwnd ^Double x ^Double y]
      (println "Mouse: (" x ", " y ")"))))

(defn mouse-scroll-callback
  []
  (proxy [GLFWScrollCallback] []
    (invoke [^Long hwnd ^Double x ^Double y]
      (println "Scroll: (" x ", " y ")"))))

(defn mouse-enter-callback
  []
  (proxy [GLFWCursorEnterCallback] []
    (invoke [^Long hwnd ^Long entered]
      (println (if (= entered gl-true)
                 "Entered"
                 "Exited")
               " window " hwnd))))

(defn build-callbacks
  [hwnd]
  (let [kb (keyboard-callback)
        button (mouse-button-callback)
        pos (mouse-position-callback)
        scroll (mouse-scroll-callback)
        entry (mouse-enter-callback)]
    ;; TODO: Don't forget the Chars callback
    (GLFW/glfwSetKeyCallback hwnd kb)
    (GLFW/glfwSetMouseButtonCallback hwnd button)
    (GLFW/glfwSetCursorPosCallback hwnd pos)
    (GLFW/glfwSetScrollCallback hwnd scroll)
    (GLFW/glfwSetCursorEnterCallback hwnd entry)
    [kb button pos scroll entry]))

(defn configure-window
  [hwnd width height]
  (let [cb (build-callbacks hwnd)]
    (let [video-mode (GLFW/glfwGetVideoMode (GLFW/glfwGetPrimaryMonitor))
          left (/ (- (GLFWvidmode/width video-mode) width) 2)
          top (/ (- (GLFWvidmode/height video-mode) height) 2)]
      (println "Putting the Window at (" left ", " top ")")
      ;; Center our window
      (GLFW/glfwSetWindowPos hwnd left top))
    ;; Make the OpenGL context current
    (println "Making " hwnd " the Current Context")
    (GLFW/glfwMakeContextCurrent hwnd)
    (println "Context assigned")
    ;; Enable v-sync
    (GLFW/glfwSwapInterval 1)
    ;; Make it visible
    (GLFW/glfwShowWindow hwnd)

    [hwnd cb]))

(defn get-window-size [^Long hwnd]
  (let [width-holder (IntBuffer/allocate 1)
        height-holder (IntBuffer/allocate 1)
        _ (GLFW/glfwGetWindowSize hwnd width-holder height-holder)
        width (.get width-holder 0)
        height (.get height-holder 0)]
    [width height]))

(defn init
  [^Long width ^Long height]
  ;; Set a default error callback. This just prints to STDERR
  (let [error-cb  (Callbacks/errorCallbackPrint System/err)]
    (GLFW/glfwSetErrorCallback error-cb)

    ;; Start by initializing GLFW...not much works before this
    (let [init-success (GLFW/glfwInit)]
      (when-not (= init-success gl-true)
        (throw (ex-info "Initialization Failure" {}))))

    ;; Configure initial window
    (GLFW/glfwDefaultWindowHints)  ; default behavior anyway
    (GLFW/glfwWindowHint GLFW/GLFW_VISIBLE gl-false)  ; Don't make immediately visible
    (GLFW/glfwWindowHint GLFW/GLFW_RESIZABLE gl-true)  ; Want it resizable

    ;; Window creation
    ;; Docs and example very specifically show passing NULL in to the monitor
    ;; and share parameters. That causes all kinds of issues.
    (let [hwnd (GLFW/glfwCreateWindow width height "Hello World!" MemoryUtil/NULL MemoryUtil/NULL)]
      (if hwnd
        (let [[width height] (get-window-size hwnd)]
          (println "Created a " width "x" height " window")
          (conj (configure-window hwnd width height) error-cb))
        (throw (ex-info "Window Creation" {}))))))

(defn run
  []
  (println "Hello LWJGL: " (Sys/getVersion) "!")

  (let [[hwnd 
         callbacks
         ^GLFWErrorCallback error-callback] (init 320 480)
         ;; One big problem with this approach: we really only want
         ;; one error callback
         [hwnd2 cb2 err2-callback] (init 640 480)]
    (try
      (println "Kicking off the event loop for " hwnd " and " hwnd2)
      (let [ev1 (future (event-loop hwnd))]
        (event-loop hwnd2)
        @ev1
        (println "Event loop exited"))
      (finally
        (try
          (GLFW/glfwDestroyWindow hwnd2)
          (doseq [cb callbacks]
            (.release cb))
          (doseq [cb cb2]
            (.release cb))
          (finally
            (GLFW/glfwTerminate)
            (.release error-callback)
            (.release err2-callback)))))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (run))
