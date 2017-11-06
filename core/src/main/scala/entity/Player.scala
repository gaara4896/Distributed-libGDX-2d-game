package my.game.pkg.entity

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array

import java.util.UUID

import my.game.pkg.entity.utils.Direction
import my.game.pkg.entity.utils.Direction._
import my.game.pkg.entity.utils.State
import my.game.pkg.assets.AssetsManager
import my.game.pkg.map.MapManager

class Player{
	val entityID = UUID.randomUUID().toString()
	val velocity = new Vector2(8f, 8f)
	var currentDirection = Direction.LEFT
	var previousDirection = Direction.UP
	var state = State.IDLE
	var frameTime:Float = 0f
	val nextPlayerPosition = new Vector2()
	val currentPlayerPosition = new Vector2()
	val boundingBox = new Rectangle()

	AssetsManager.loadTextureAsset(Player.defaultSpritePatch)
	val texture:Option[Texture] = AssetsManager.getTextureAsset(Player.defaultSpritePatch)
	val walkDownFrames = new Array[TextureRegion](4)
	val walkLeftFrames = new Array[TextureRegion](4)
	val walkRightFrames = new Array[TextureRegion](4)
	val walkUpFrames = new Array[TextureRegion](4)
	texture match{
		case Some(tex) => 
			for(x <- 0 to 3; val textureFrames:scala.Array[scala.Array[TextureRegion]] = TextureRegion.split(tex, Player.FRAME_WIDTH, Player.FRAME_HEIGHT)){
				for(y <- 0 to 3){
					x match{
						case 0 => walkDownFrames.insert(y, textureFrames(x)(y))
						case 1 => walkLeftFrames.insert(y, textureFrames(x)(y))
						case 2 => walkRightFrames.insert(y, textureFrames(x)(y))
						case 3 => walkUpFrames.insert(y, textureFrames(x)(y))
					}
				}
			}
	}
	val walkDownAnimation = new Animation[TextureRegion](0.25f, walkDownFrames, Animation.PlayMode.LOOP)
	val walkLeftAnimation = new Animation[TextureRegion](0.25f, walkLeftFrames, Animation.PlayMode.LOOP)
	val walkRightAnimation = new Animation[TextureRegion](0.25f, walkRightFrames, Animation.PlayMode.LOOP)
	val walkUpAnimation = new Animation[TextureRegion](0.25f, walkUpFrames, Animation.PlayMode.LOOP)

	var currentFrame = walkDownFrames.get(0)
	val frameSprite = new Sprite(currentFrame.getTexture(), 0, 0, Player.FRAME_WIDTH, Player.FRAME_HEIGHT)

	def update(delta:Float){
		frameTime = (frameTime + delta)%5
		setBoundingSize(0f, 0.5f)
	}

	def init(x:Int, y:Int){
		currentPlayerPosition.x = x
		currentPlayerPosition.y = y
		nextPlayerPosition.x = x
		nextPlayerPosition.y = y
	}

	def setBoundingSize(widthReduce:Float, heightReduce:Float){
		val width = Player.FRAME_WIDTH * (1.0f - widthReduce)
		val height = Player.FRAME_HEIGHT * (1.0f - heightReduce)

		if(width == 0 || height == 0){
			Gdx.app.debug(Player.TAG, s"Width and Height are 0! $width:$height")
		}

		val minX = nextPlayerPosition.x / MapManager.UNIT_SCALE
		val minY = nextPlayerPosition.y / MapManager.UNIT_SCALE

		boundingBox.set(minX, minY, width, height)
	}

	def dispose(){
		AssetsManager.unloadAsset(Player.defaultSpritePatch)
	}

	def setCurrentPosition(x:Float, y:Float){
		frameSprite.setX(x)
		frameSprite.setY(y)
		currentPlayerPosition.x = x
		currentPlayerPosition.y = y
	}

	def setDirection(direction:Direction){
		previousDirection = currentDirection
		currentDirection = direction

		currentDirection match{
			case Direction.DOWN => currentFrame = walkDownAnimation.getKeyFrame(frameTime)
			case Direction.LEFT => currentFrame = walkLeftAnimation.getKeyFrame(frameTime)
			case Direction.RIGHT => currentFrame = walkRightAnimation.getKeyFrame(frameTime)
			case Direction.UP => currentFrame = walkUpAnimation.getKeyFrame(frameTime)
		}
	}

	def calculateNextPosition(currentDirection:Direction, delta:Float){
		velocity.scl(delta)

		currentDirection match{
			case Direction.LEFT => nextPlayerPosition.x = currentPlayerPosition.x - velocity.x
			case Direction.RIGHT => nextPlayerPosition.x = currentPlayerPosition.x + velocity.x
			case Direction.UP => nextPlayerPosition.y = currentPlayerPosition.y + velocity.y
			case Direction.DOWN => nextPlayerPosition.y = currentPlayerPosition.y - velocity.y
			case _ => 
		}
		velocity.scl(1 / delta)
	}
}

object Player{

	def apply():Player = new Player()

	val TAG:String = Player.getClass().getSimpleName()
	val defaultSpritePatch:String = "sprites/characters/Warrior.png"
	val FRAME_WIDTH = 16
	val FRAME_HEIGHT = 16
}