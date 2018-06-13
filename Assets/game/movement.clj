(ns game.movement
  (:use arcadia.core arcadia.linear)
  (:import [UnityEngine Collider2D Physics
            GameObject Input Rigidbody2D Rigidbody
            Vector2 Mathf Resources Transform
            Collision2D Physics2D]
           ArcadiaState))

 (defn bearing-vector [angle]
   (let [angle (* Mathf/Deg2Rad angle)]
     (v3 (Mathf/Cos angle) (Mathf/Sin angle) 1)))

 (defn abs-angle [v]
   (* Mathf/Rad2Deg
      (Mathf/Atan2 (.y v) (.x v))))

 (defn controller-vector []
  (v2 (Input/GetAxis "Horizontal")
      (Input/GetAxis "Vertical")))

 (defn wasd-key []
   (or (Input/GetKey "w")
       (Input/GetKey "a")
       (Input/GetKey "s")
       (Input/GetKey "d")))

(defn move []
  (v3 (Input/GetAxis "Horizontal") 0 (Input/GetAxis "Vertical")))

 (defn player-movement-fixed-update [obj k] ; We'll only use the `obj` parameter
   (with-cmpt obj [rb Rigidbody]          ; Gets the Rigidbody2D component
     (when (wasd-key)
      (set! (.velocity rb) (move))

    )))
