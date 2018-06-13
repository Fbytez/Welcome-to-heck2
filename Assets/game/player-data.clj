(ns game.player-data
  (:use arcadia.core arcadia.linear)
  (:import [UnityEngine Collider2D Physics
            GameObject Input Rigidbody2D Rigidbody
            Vector2 Mathf Resources Transform
            Collision2D Physics2D]
           ArcadiaState))

(def PLAYER-DATA
  (atom {
    :health 100
    }))
