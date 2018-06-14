(ns game.core
  (:use arcadia.core arcadia.linear)
  (:import [UnityEngine Collider2D Physics
            GameObject Input Rigidbody2D Rigidbody
            Vector2 Mathf Resources Transform
            Collision2D Physics2D Time KeyCode]
           ArcadiaState))

(declare setup)

(defn bearing-vector [angle]
  (let [angle (* Mathf/Deg2Rad angle)]
    (v3 (Mathf/Cos angle) (Mathf/Sin angle) 1)))


(defn abs-angle [v]
  (* Mathf/Rad2Deg
     (Mathf/Atan2 (.y v) (.x v))))

(defn controller-vector-1 []
 (v2 (Input/GetAxis "Horizontal_P1")
     (Input/GetAxis "Vertical_P2")))

     (defn controller-vector-2 []
      (v2 (Input/GetAxis "Horizontal_P2")
          (Input/GetAxis "Vertical_P2")))


(defn wasd-key []
  (or (Input/GetKey "w")
      (Input/GetKey "a")
      (Input/GetKey "s")
      (Input/GetKey "d")))

(defn ijkl-key []
  (or (Input/GetKey "i")
      (Input/GetKey "j")
      (Input/GetKey "k")
      (Input/GetKey "l")))

(defn move_P1 []
 (v3  (Input/GetAxis "Horizontal_P1") 0 (Input/GetAxis "Vertical_P1")))

 (defn move_P2 []
  (v3 (Input/GetAxis "Horizontal_P2") 0 (Input/GetAxis "Vertical_P2")))

(defn player-movement-fixed-update [obj k] ; We'll only use the `obj` parameter
  (with-cmpt obj [rb Rigidbody]          ; Gets the Rigidbody2D component
    (when (wasd-key)
     (set! (.velocity rb) (move_P1))
     (. rb (AddForce (move_P1))))
     (if (Input/GetKeyUp (KeyCode/Space)) (do (log "aw heck")(. rb (AddForce (v3 0.0 1000.0 0.0)))))))

     (defn player2-movement-fixed-update [obj k] ; We'll only use the `obj` parameter
       (with-cmpt obj [rb Rigidbody]          ; Gets the Rigidbody2D component
         (when (ijkl-key)
          (set! (.velocity rb) (move_P2))
          (. rb (AddForce (move_P2)))
          (if (Input/GetKeyUp (KeyCode/RightControl)) (do (log "aw heck")(. rb (AddForce (v3 0.0 1000.0 0.0))))))))



(def player-roles
  {::movement {:fixed-update #'player-movement-fixed-update}})

  (def player-roles2
    {::movement {:fixed-update #'player2-movement-fixed-update}})

(def gamemaster-roles
    {::setup {:start #'setup}})

(defn follow-player-role [obj k]
  (with-cmpt obj [t Transform]
    (as-> (v3+ (.position (cmpt (:player (state obj k)) UnityEngine.Transform)) (:offset (state obj k))) $
            (set! (.position t) $))))


(defn game-master-setup []
  (let [gamemaster  (object-named "GameMaster")]
    (roles+ gamemaster gamemaster-roles)
    ))

(defn setup [_ _]
  (let [player  (object-named "WoodenBall")
        player2 (object-named "WoodenBall2")
        cam1 (object-named "player1-cam")
        cam2 (object-named "player2-cam")]

        (roles+ cam1
            {::follow-player
               {:state {:player player
                        :offset (v3- (.position (cmpt (object-named "player1-cam") UnityEngine.Transform))
                                     (.position (cmpt (object-named "unitychan") UnityEngine.Transform)))}
                :update #'follow-player-role}})

                (roles+ cam2
                    {::follow-player
                       {:state {:player player2
                                :offset (v3- (.position (cmpt (object-named "player2-cam") UnityEngine.Transform))
                                             (.position (cmpt (object-named "unitychan2") UnityEngine.Transform)))}
                        :update #'follow-player-role}})

    (roles+ player player-roles)
    (roles+ player2 player-roles2)
    )) ; NEW
