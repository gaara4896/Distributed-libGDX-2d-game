package my.game.pkg.entity

import com.badlogic.gdx.math.Vector2

import my.game.pkg.entity.utils.{Direction, State}
import my.game.pkg.entity.utils.Direction._
import my.game.pkg.entity.utils.Job._

class RemoteNPC(val uuid:String, job:Job, direction:Direction) extends NPC(job, direction){
	
	val velocity = new Vector2(3f, 3f)
	var countDownRange:Float = 0

	/**
	 * Update NPC status
	 * @param  delta:Float Delta time fo the frame
	 */
	def update(delta:Float){
		state match{
			case State.IDLE => 
			case State.WALKING => 
				frameTime += delta
				velocity.scl(delta)
				currentDirection match{
					case Direction.LEFT =>
						position.x -= velocity.x
						frameSprite.setX(position.x)
						countDownRange -= velocity.x						
					case Direction.RIGHT =>
						position.x += velocity.x
						frameSprite.setX(position.x)
						countDownRange -= velocity.x
					case Direction.UP =>
						position.y += velocity.y
						frameSprite.setY(position.y)
						countDownRange -= velocity.y
					case Direction.DOWN =>
						position.y -= velocity.y
						frameSprite.setY(position.y)
						countDownRange -= velocity.y
					case _ =>
				}
				currentFrame = EntitySprite.getAnimation(job, currentDirection).getKeyFrame(frameTime)
				velocity.scl(1 / delta)
				if(countDownRange <= 0){
					state = State.IDLE
				}
		}
	}
}

object RemoteNPC{

	/**
	 * Apply method for creating RemoteNPC
	 * @param  uuid:String         UUID of the NPC
	 * @param  job:Job             Job of the NPC
	 * @param  direction:Direction Direction of the NPC
	 * @param  x:Float             X position of NPC
	 * @param  y:Float             Y position of NPC
	 * @param  range:Float         Range NPC going to move
	 * @return RemoteNPC           New instance of RemoteNPC
	 */
	def apply(uuid:String, job:Job, direction:Direction, x:Float, y:Float, range:Float): RemoteNPC = {
		new RemoteNPC(uuid, job, direction){
			position.x = x
			position.y = y
			frameSprite.setX(x)
			frameSprite.setY(y)
			if(range > 0){
				countDownRange = range
				state = State.WALKING
			}
		}
	}

	private val TAG:String = RemoteNPC.getClass().getSimpleName()
}