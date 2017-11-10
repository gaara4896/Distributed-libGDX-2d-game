package my.game.pkg.entity

import com.badlogic.gdx.graphics.g2d.Sprite

import my.game.pkg.entity.utils.Direction
import my.game.pkg.entity.utils.Direction._

class RemotePlayer(val uuid:String, x:Float, y:Float, direction:Direction, frame:Float) extends PlayerEntity {

	var currentDirection = direction
	var frameTime = frame
	val frameSprite = new Sprite(currentFrame.getTexture(), x.asInstanceOf[Int], y.asInstanceOf[Int], PlayerEntity.FRAME_WIDTH, PlayerEntity.FRAME_HEIGHT)
	/**
	 * Update the player to latest status
	 * @param x:Float             X position of player
	 * @param y:Float             Y position of player
	 * @param direction:Direction Direction of the player
	 * @param frame:Float         Frame time of player
	 */
	def update(x:Float, y:Float, direction:Direction, frame:Float){
		frameSprite.setX(x)
		frameSprite.setY(y)
		frameTime = frame
		currentDirection = direction

		currentDirection match{
			case Direction.LEFT =>
				currentFrame = PlayerEntity.walkLeftAnimation.getKeyFrame(frameTime)
			case Direction.RIGHT =>
				currentFrame = PlayerEntity.walkRightAnimation.getKeyFrame(frameTime)
			case Direction.UP =>
				currentFrame = PlayerEntity.walkUpAnimation.getKeyFrame(frameTime)
			case Direction.DOWN =>
				currentFrame = PlayerEntity.walkDownAnimation.getKeyFrame(frameTime)
			case _ =>
		}
	}
}

object RemotePlayer{
	/**
	 * Apply method for creating RemotePlayer
	 * @param  uuid:String         UUID of the player  
	 * @param  x:Float             Y position of the player
	 * @param  y:Float             X position of the player
	 * @param  direction:Direction Direction of the player
	 * @param  frame:Float         Frame time of the player
	 * @return RemotePlayer        New instance of RemotePlayer
	 */
	def apply(uuid:String, x:Float, y:Float, direction:Direction, frame:Float):RemotePlayer = new RemotePlayer(uuid, x, y, direction, frame)

	private val TAG:String = RemotePlayer.getClass().getSimpleName()
	private val defaultSpritePatch:String = "sprites/characters/Rogue.png"
}
