(ns game.core
  (:use arcadia.core arcadia.linear)
  (:import [UnityEngine Collider2D Physics
            GameObject Input Rigidbody2D Rigidbody
            Vector2 Mathf Resources Transform
            Collision2D Physics2D]
           ArcadiaState
           )
  (:require [game.movement :as m])
  )

  (def player-roles
    {::movement {:fixed-update #'game.movement/player-movement-fixed-update}
     ::stats {:health 10}}
    )

  (defn setup [go k]
    (let [player  (object-named "unitychan")
          ;add new stuff here
          ]
        ;do more things here
      (roles+ player player-roles)
      ))


;To adds/edit hooks onto GameMaster
;Before executing, delete existing corresponding scripts on GameMaster object

  ; (def gamemaster-roles
  ;   {::setup {:start #'game.core/setup}})

  ; (defn game-master-setup []
  ;   (let [gamemaster  (object-named "GameMaster")]
  ;     (roles+ gamemaster gamemaster-roles)
  ;     ))
