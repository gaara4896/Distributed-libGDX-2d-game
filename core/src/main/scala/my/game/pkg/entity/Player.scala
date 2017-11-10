package my.game.pkg.entity

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.{Rectangle, Vector2}

import my.game.pkg.Distributedlibgdx2dgame
import my.game.pkg.entity.utils.Direction
import my.game.pkg.entity.utils.Direction._
import my.game.pkg.entity.utils.State
import my.game.pkg.entity.utils.State._
import my.game.pkg.assets.AssetsManager
import my.game.pkg.map.MapManager

class Player extends PlayerEntity {
	val velocity = new Vector2(10f, 10f)
	var currentDirection = Direction.LEFT
	var previousDirection = Direction.UP
	var frameTime:Float = 0f
	var state = State.IDLE
	val currentPlayerPosition = new Vector2()
	val nextPlayerPosition = new Vector2()
	val boundingBox = new Rectangle() 
	val frameSprite = new Sprite(currentFrame.getTexture(), 0, 0, PlayerEntity.FRAME_WIDTH, PlayerEntity.FRAME_HEIGHT)

	/**
	 * Update the player to latest status
	 * @param  delta:Float         Delta time value of the frame
	 * @param  direction:Direction Direction of the player
	 * @param  currentState:State  State of the player
	 */
	def update(delta:Float, direction:Direction, currentState:State){
		frameTime = (frameTime + delta)%5
		previousDirection = currentDirection
		currentDirection = direction
		state = currentState

		velocity.scl(delta)
		if(currentState != State.IDLE){
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

	/**
	 * Overloading method for updating the player status
	 * @param  delta:Float        Delta time value of the frame
	 * @param  currentState:State State of the player
	 */
	def update(delta:Float, currentState:State){
		update(delta, currentDirection, currentState)
	}

	/**
	 * Initialize position of the player
	 * @param  position:Vector2 Position of the player
	 */
	def init(position:Vector2){
		currentPlayerPosition.x = position.x.toInt
		currentPlayerPosition.y = position.y.toInt
		nextPlayerPosition.x = position.x.toInt
		nextPlayerPosition.y = position.y.toInt
	}

	/**
	 * Set bounding box size for the player
	 * @param widthReduce:Float  Width reduced in percentage
	 * @param heightReduce:Float Height reduced in percentage
	 */
	def setBoundingSize(widthReduce:Float, heightReduce:Float){
		val width = PlayerEntity.FRAME_WIDTH * (1.0f - widthReduce)
		val height = PlayerEntity.FRAME_HEIGHT * (1.0f - heightReduce)

		if(width == 0 || height == 0){
			Gdx.app.debug(Player.TAG, s"Width and Height are 0! $width:$height")
		}

		val minX = nextPlayerPosition.x / MapManager.UNIT_SCALE
		val minY = nextPlayerPosition.y / MapManager.UNIT_SCALE

		boundingBox.set(minX, minY, width, height)
	}

	/**
	 * Dispose of asset when not needed
	 */
	def dispose(){
		AssetsManager.unloadAsset(PlayerEntity.defaultSpritePatch)
	}

	/**
	 * Move player
	 */
	def move(game:Distributedlibgdx2dgame, mapName:String){
		currentPlayerPosition.x = nextPlayerPosition.x
		currentPlayerPosition.y = nextPlayerPosition.y
		frameSprite.setX(currentPlayerPosition.x)
		frameSprite.setY(currentPlayerPosition.y)
		game.client match{
			case Some(x) => game.gameUUID.foreach{uuid =>
					x.sendStatus(uuid, mapName, currentPlayerPosition.x, currentPlayerPosition.y, currentDirection, frameTime)
				}
			case None => 
		}
	}
}

object Player{

	/**
	 * Apply method for creating Player
	 * @return Player New instance of Player
	 */
	def apply():Player = new Player()

	private val TAG:String = Player.getClass().getSimpleName()
}