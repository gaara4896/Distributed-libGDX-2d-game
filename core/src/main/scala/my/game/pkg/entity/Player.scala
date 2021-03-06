package my.game.pkg.entity

import com.badlogic.gdx.math.Vector2

import my.game.pkg.Distributedlibgdx2dgame
import my.game.pkg.screen.MainGameScreen
import my.game.pkg.entity.utils.Direction
import my.game.pkg.entity.utils.Direction._
import my.game.pkg.entity.utils.State
import my.game.pkg.entity.utils.State._
import my.game.pkg.entity.utils.Job._

class Player(job:Job) extends PlayerEntity(job) {
	
	/**
	 * Update the player to latest status
	 * @param delta:Float                  Delta time value of the frame
	 * @param direction:Direction          Direction of the player
	 * @param currentState:State           State of the player
	 * @param game:Distributedlibgdx2dgame Main game class
	 */
	def update(delta:Float, direction:Direction, currentState:State, game:Distributedlibgdx2dgame){
		frameTime = (frameTime + delta)%5
		previousDirection = currentDirection
		currentDirection = direction
		state = currentState

		velocity.scl(delta)
		if(currentState != State.IDLE){
			currentDirection match{
				case Direction.LEFT =>
					nextPosition.x = position.x - velocity.x
					nextPosition.y = position.y	
				case Direction.RIGHT =>
					nextPosition.x = position.x + velocity.x
					nextPosition.y = position.y
				case Direction.UP =>
					nextPosition.y = position.y + velocity.y
					nextPosition.x = position.x
				case Direction.DOWN =>
					nextPosition.y = position.y - velocity.y
					nextPosition.x = position.x
				case _ =>
			}
			currentFrame = EntitySprite.getAnimation(job, direction).getKeyFrame(frameTime)
		}
		velocity.scl(1 / delta)
		setBoundingSize(0f, 0.5f)
		game.client match{
			case Some(x) => x.update(delta, job, MainGameScreen.mapMgr.currentMapName, position.x, position.y, currentDirection, currentState:State, frameTime)
			case None => 
		}
	}

	/**
	 * Overloading method for updating the player status
	 * @param delta:Float                  Delta time value of the frame
	 * @param currentState:State           State of the player
	 * @param game:Distributedlibgdx2dgame Main game class
	 */
	def update(delta:Float, currentState:State, game:Distributedlibgdx2dgame){
		update(delta, currentDirection, currentState, game)
	}

	/**
	 * Initialize position of the player
	 * @param  position:Vector2 Position of the player
	 */
	def init(position:Vector2){
		position.x = position.x.toInt
		position.y = position.y.toInt
		nextPosition.x = position.x.toInt
		nextPosition.y = position.y.toInt
	}

	/**
	 * Dispose of asset when not needed
	 */
	def dispose(){
		EntitySprite.dispose()
	}
}

object Player{

	/**
		* Apply method for creating Player
		* @param  job:Job Job of the Player
		* @return Player  New instance of Player
		*/
	def apply(job:Job):Player = new Player(job)

	private val TAG:String = Player.getClass().getSimpleName()
}