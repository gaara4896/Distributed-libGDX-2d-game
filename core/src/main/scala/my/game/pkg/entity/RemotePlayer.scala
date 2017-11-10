package my.game.pkg.entity

import com.badlogic.gdx.graphics.g2d.Sprite

import my.game.pkg.entity.utils.Direction
import my.game.pkg.entity.utils.Direction._

class RemotePlayer(val gameUUID:String, x:Float, y:Float, direction:Direction, frame:Float) extends PlayerEntity {

	var currentDirection = direction
	var frameTime = frame
	val frameSprite = new Sprite(currentFrame.getTexture(), x.asInstanceOf[Int], y.asInstanceOf[Int], PlayerEntity.FRAME_WIDTH, PlayerEntity.FRAME_HEIGHT)
	/**
	 * Update the player to latest status
	 * @param  delta:Float         Delta time value of the frame
	 * @param  direction:Direction Direction of the player
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
	 * @return RemotePlayer New instance of RemotePlayer
	 */
	def apply(gameUUID:String, x:Float, y:Float, direction:Direction, frame:Float):RemotePlayer = new RemotePlayer(gameUUID, x, y, direction, frame)

	private val TAG:String = RemotePlayer.getClass().getSimpleName()
	private val defaultSpritePatch:String = "sprites/characters/Rogue.png"
}
