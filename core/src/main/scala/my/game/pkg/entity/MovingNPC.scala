package my.game.pkg.entity

import my.game.pkg.entity.utils.{Direction, State}
import my.game.pkg.entity.utils.State.State

//Xmax, Xmin, Ymax and Ymin provides the coordinate for the square movement of the NPC
class MovingNPC (spritePatch : String, mapName : String, val Xmax : Float, val Xmin : Float, val Ymax : Float, val Ymin : Float) extends NPC(spritePatch, mapName, Xmax, Ymax){

    var directionToGo = currentDirection
    var moveCount : Int = 0

    //moving the NPC in square according to given coordinates
    override def update(delta:Float, currentState:State){
        if(currentPlayerPosition.x >= Xmax-0.5 && currentPlayerPosition.x <= Xmax+0.5 && currentDirection == Direction.RIGHT){
          directionToGo = Direction.DOWN
        }
        else if(currentPlayerPosition.x >= Xmin-0.5 && currentPlayerPosition.x <= Xmin+0.5 && currentDirection == Direction.LEFT){
          directionToGo = Direction.UP
        }
        else if(currentPlayerPosition.y >= Ymax-0.5 && currentPlayerPosition.y <= Ymax+0.5 && currentDirection == Direction.UP){
          directionToGo = Direction.RIGHT
        }
        else if(currentPlayerPosition.y >= Ymin-0.5 && currentPlayerPosition.y <= Ymin+0.5 && currentDirection == Direction.DOWN){
          directionToGo = Direction.LEFT
        }
        else{
          update(delta/5, currentDirection, currentState)
          return
        }

        //make the NPC wait for 50 frames before moving to another direction
        if(moveCount >= 50){
          moveCount = 0
          update(delta/5, directionToGo, currentState)
        }
        else {
          moveCount = moveCount + 1
          update(delta, currentDirection, State.IDLE)
        }

    }

    def update(delta : Float): Unit ={
        update(delta, State.WALKING)
    }
}

object MovingNPC{
    /**
      * Apply method for creating NPC
      * @return NPC New instance of NPC
      */
    def apply(spritePatch : String, mapName : String, Xmax : Float, Xmin : Float, Ymax : Float, Ymin : Float):MovingNPC = new MovingNPC(spritePatch, mapName, Xmax, Xmin, Ymax, Ymin)

    private val TAG:String = MovingNPC.getClass().getSimpleName()
}