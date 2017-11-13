package my.game.pkg.entity

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Vector2

import my.game.pkg.entity.utils.Direction._
import my.game.pkg.entity.utils.Job._

class NPC (job:Job, direction:Direction) extends Entity(job){

	var currentDirection = direction
	var currentFrame = EntitySprite.getFirstTexture(job, currentDirection)
	val frameSprite = new Sprite(currentFrame.getTexture(), 0, 0, Entity.FRAME_WIDTH, Entity.FRAME_HEIGHT)

	/**
	 * Initialize NPC position
	 * @param  initPosition:Vector2 Position of NPC to be initialize
	 */
	def init(initPosition:Vector2){
		position.x = initPosition.x
		position.y = initPosition.y
		frameSprite.setX(initPosition.x)
		frameSprite.setY(initPosition.y) 
	}

}

object NPC{
	/**
	 * Apply method for creating NPC
	 * @return NPC New instance of NPC
	 */
	def apply(job:Job, direction:Direction):NPC = new NPC(job, direction)

	private val TAG:String = NPC.getClass().getSimpleName()
}
