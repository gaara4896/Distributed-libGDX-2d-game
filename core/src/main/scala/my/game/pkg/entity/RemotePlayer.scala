package my.game.pkg.entity

import my.game.pkg.entity.utils.Direction
import my.game.pkg.entity.utils.Direction._
import my.game.pkg.entity.utils.State
import my.game.pkg.entity.utils.State._

class RemotePlayer(val uuid:String, x:Float, y:Float) extends PlayerEntity {

	currentPlayerPosition.x = x
	currentPlayerPosition.y = y

	/**
	 * Update the player to latest status
	 * @param x:Float             X position of player
	 * @param y:Float             Y position of player
	 * @param direction:Direction Direction of the player
	 * @param frame:Float         Frame time of player
	 */
	/**
	 * Update the player to latest status
	 * @param  delta:Float         Delta time value of the frame
	 */
	def update(delta:Float){
		frameTime = (frameTime + delta)%5

		velocity.scl(delta)
		if(state != State.IDLE){
			currentDirection match{
				case Direction.LEFT =>
					nextPlayerPosition.x = currentPlayerPosition.x - velocity.x
					nextPlayerPosition.y = currentPlayerPosition.y
					currentFrame = PlayerEntity.walkLeftAnimation.getKeyFrame(frameTime)
				case Direction.RIGHT =>
					nextPlayerPosition.x = currentPlayerPosition.x + velocity.x
					nextPlayerPosition.y = currentPlayerPosition.y
					currentFrame = PlayerEntity.walkRightAnimation.getKeyFrame(frameTime)
				case Direction.UP =>
					nextPlayerPosition.y = currentPlayerPosition.y + velocity.y
					nextPlayerPosition.x = currentPlayerPosition.x
					currentFrame = PlayerEntity.walkUpAnimation.getKeyFrame(frameTime)
				case Direction.DOWN =>
					nextPlayerPosition.y = currentPlayerPosition.y - velocity.y
					nextPlayerPosition.x = currentPlayerPosition.x
					currentFrame = PlayerEntity.walkDownAnimation.getKeyFrame(frameTime)
				case _ =>
			}
		}
		velocity.scl(1 / delta)
		setBoundingSize(0f, 0.5f)
	}

	def setMove(direction:Direction){
		state = State.WALKING
		currentDirection = direction
	}

	def setStandStill(x:Float, y:Float){
		state = State.IDLE
		currentPlayerPosition.x = x
		currentPlayerPosition.y = y
	}

	def correction(x:Float, y:Float, direction:Direction, playerState:State){
		currentPlayerPosition.x = x
		currentPlayerPosition.y = y
		frameSprite.setX(x)
		frameSprite.setY(y)
		currentDirection = direction
		state = playerState
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
	def apply(uuid:String, x:Float, y:Float):RemotePlayer = new RemotePlayer(uuid, x, y)

	private val TAG:String = RemotePlayer.getClass().getSimpleName()
}
