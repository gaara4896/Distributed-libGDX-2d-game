package my.game.pkg.entity

import com.badlogic.gdx.math.Vector2

import my.game.pkg.entity.utils.{Direction, State}
import my.game.pkg.entity.utils.Direction._
import my.game.pkg.entity.utils.Job._

import scala.collection.mutable.Queue
import scala.util.Random

//Xmax, Xmin, Ymax and Ymin provides the coordinate for the square movement of the NPC
class MovingNPC (job:Job, val rangeX:Float, val rangeY:Float) extends NPC(job, Direction.RIGHT){

	val velocity = new Vector2(3f, 3f)
	var directionSequence = Queue(Direction.DOWN, Direction.LEFT, Direction.UP)
	var walkingDirection:Direction = Direction.RIGHT
	var countDownRange:Float = rangeX
	var restTime:Float = 0
	state = State.WALKING

	/**
	 * Update NPC status
	 * @param  delta:Float Delta time of the frame
	 */
	def update(delta:Float){
		state match{
			case State.IDLE => 
				restTime -= delta
				if(restTime <= 0) {
					state = State.WALKING
					directionSequence += walkingDirection
					walkingDirection = directionSequence.dequeue()
					if(walkingDirection == Direction.RIGHT || walkingDirection == Direction.LEFT){
						countDownRange = rangeX
					} else {
						countDownRange = rangeY
					}
				}
			case State.WALKING => 
				frameTime += delta
				velocity.scl(delta)
				walkingDirection match{
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
				currentFrame = EntitySprite.getAnimation(job, walkingDirection).getKeyFrame(frameTime)
				velocity.scl(1 / delta)
				if(countDownRange <= 0){
					state = State.IDLE
					restTime = 1f + (Random.nextFloat % 5f)
				}
		}
	}
}

object MovingNPC{

	/**
	  * Apply method for creating MovingNPC
	  * @param  spritePatch:String Sprite patch of the NPC
	  * @param  rangeX:Float       Range in X of the NPC will move
	  * @param  rangeY:Float       Range in Y of the NPC will move
	  * @return MovingNPC          New instance of MovingNPC
	  */
	def apply(job:Job, rangeX:Float, rangeY:Float):MovingNPC = new MovingNPC(job, rangeX, rangeY)

	private val TAG:String = MovingNPC.getClass().getSimpleName()
}