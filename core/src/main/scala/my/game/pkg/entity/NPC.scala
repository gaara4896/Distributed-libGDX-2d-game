/*package my.game.pkg.entity

import com.badlogic.gdx.math.Vector2
import my.game.pkg.entity.utils.Direction.Direction

//positionX and position Y are for the coordinate of the NPC
class NPC (spritePatch : String, val mapName : String, val positionX : Float, val positionY : Float) extends PlayerEntity(spritePatch){

    init(new Vector2(positionX, positionY))

}

object NPC{
  	/**
     * Apply method for creating NPC
     * @return NPC New instance of NPC
     */
 	def apply(spritePatch : String, mapName : String, positionX : Float, positionY : Float, direction : Direction):NPC = new NPC(spritePatch, mapName, positionX, positionY){currentDirection = direction}

 	private val TAG:String = NPC.getClass().getSimpleName()
}
*/