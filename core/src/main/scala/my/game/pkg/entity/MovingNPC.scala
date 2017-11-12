package my.game.pkg.entity

import com.badlogic.gdx.graphics.g2d.{Animation, TextureRegion}
import com.badlogic.gdx.math.Vector2

import my.game.pkg.entity.utils.{Direction, State}
import my.game.pkg.entity.utils.Direction._

import scala.collection.mutable.Queue
import scala.util.Random

//Xmax, Xmin, Ymax and Ymin provides the coordinate for the square movement of the NPC
class MovingNPC (spritePatch:String, val rangeX:Float, val rangeY:Float) extends NPC(spritePatch, Direction.RIGHT){

	val velocity = new Vector2(3f, 3f)
	var directionSequence = Queue(Direction.DOWN, Direction.LEFT, Direction.UP)
	var walkingDirection:Direction = Direction.RIGHT
	var countDownRange:Float = rangeX
	var restTime:Float = 0
	state = State.WALKING

	val walkDownAnimation = new Animation[TextureRegion](0.25f, walkDownFrames, Animation.PlayMode.LOOP)
	val walkLeftAnimation = new Animation[TextureRegion](0.25f, walkLeftFrames, Animation.PlayMode.LOOP)
	val walkRightAnimation = new Animation[TextureRegion](0.25f, walkRightFrames, Animation.PlayMode.LOOP)
	val walkUpAnimation = new Animation[TextureRegion](0.25f, walkUpFrames, Animation.PlayMode.LOOP)

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
						currentFrame = walkLeftAnimation.getKeyFrame(frameTime)
					case Direction.RIGHT =>
						position.x += velocity.x
						frameSprite.setX(position.x)
						countDownRange -= velocity.x
						currentFrame = walkRightAnimation.getKeyFrame(frameTime)
					case Direction.UP =>
						position.y += velocity.y
						frameSprite.setY(position.y)
						countDownRange -= velocity.y
						currentFrame = walkUpAnimation.getKeyFrame(frameTime)
					case Direction.DOWN =>
						position.y -= velocity.y
						frameSprite.setY(position.y)
						countDownRange -= velocity.y
						currentFrame = walkDownAnimation.getKeyFrame(frameTime)
					case _ =>
				}
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
	def apply(spritePatch:String, rangeX:Float, rangeY:Float):MovingNPC = new MovingNPC(spritePatch, rangeX, rangeY)

	private val TAG:String = MovingNPC.getClass().getSimpleName()
}