package my.game.pkg.entity

import my.game.pkg.entity.utils.Direction
import my.game.pkg.entity.utils.Direction._
import my.game.pkg.entity.utils.State
import my.game.pkg.entity.utils.State._

class RemotePlayer(val uuid:String, x:Float, y:Float) extends PlayerEntity {

	position.x = x
	position.y = y

	/**
	 * Update the player to latest status
	 * @param  delta:Float Delta time value of the frame
	 */
	def update(delta:Float){
		frameTime = (frameTime + delta)%5

		velocity.scl(delta)
		if(state != State.IDLE){
			currentDirection match{
				case Direction.LEFT =>
					nextPosition.x = position.x - velocity.x
					nextPosition.y = position.y
					currentFrame = PlayerEntity.walkLeftAnimation.getKeyFrame(frameTime)
				case Direction.RIGHT =>
					nextPosition.x = position.x + velocity.x
					nextPosition.y = position.y
					currentFrame = PlayerEntity.walkRightAnimation.getKeyFrame(frameTime)
				case Direction.UP =>
					nextPosition.y = position.y + velocity.y
					nextPosition.x = position.x
					currentFrame = PlayerEntity.walkUpAnimation.getKeyFrame(frameTime)
				case Direction.DOWN =>
					nextPosition.y = position.y - velocity.y
					nextPosition.x = position.x
					currentFrame = PlayerEntity.walkDownAnimation.getKeyFrame(frameTime)
				case _ =>
			}
		}
		velocity.scl(1 / delta)
		setBoundingSize(0f, 0.5f)
	}

	/**
	 * Set remote player to move state
	 * @param direction:Direction Direction of the player
	 */
	def setMove(direction:Direction){
		state = State.WALKING
		currentDirection = direction
	}

	/**
	 * Set remote player to stand still state
	 * @param x:Float X position of the player
	 * @param y:Float Y position of the player
	 */
	def setStandStill(x:Float, y:Float){
		state = State.IDLE
		position.x = x
		position.y = y
	}

	/**
	 * Correction on remote player status
	 * @param  x:Float             X position of remote player
	 * @param  y:Float             Y position of remote player
	 * @param  direction:Direction Direction of remote player
	 * @param  playerState:State   State of the remote player
	 * @param  frame:Float         Time of the frame
	 */
	def correction(x:Float, y:Float, direction:Direction, playerState:State, frame:Float){
		position.x = x
		position.y = y
		frameSprite.setX(x)
		frameSprite.setY(y)
		currentDirection = direction
		state = playerState
		frameTime = frame
	}
}

object RemotePlayer{
	/**
	 * Apply method for creating RemotePlayer
	 * @param  uuid:String         UUID of the player  
	 * @param  x:Float             Y position of the player
	 * @param  y:Float             X position of the player
	 * @return RemotePlayer        New instance of RemotePlayer
	 */
	def apply(uuid:String, x:Float, y:Float):RemotePlayer = new RemotePlayer(uuid, x, y)

	private val TAG:String = RemotePlayer.getClass().getSimpleName()
}
