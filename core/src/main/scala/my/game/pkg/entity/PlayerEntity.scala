package my.game.pkg.entity

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{Animation, Sprite, TextureRegion}
import com.badlogic.gdx.math.{Rectangle, Vector2}
import com.badlogic.gdx.utils.Array

import my.game.pkg.assets.AssetsManager
import my.game.pkg.entity.utils.Direction
import my.game.pkg.entity.utils.State
import my.game.pkg.map.MapManager

abstract class PlayerEntity {
	val velocity = new Vector2(10f, 10f)
	var currentDirection = Direction.LEFT
	var previousDirection = Direction.UP
	var frameTime:Float = 0f
	var state = State.IDLE
	val currentPlayerPosition = new Vector2()
	val nextPlayerPosition = new Vector2()
	val boundingBox = new Rectangle()
	var currentFrame:TextureRegion = PlayerEntity.walkDownFrames.get(0)
	val frameSprite = new Sprite(currentFrame.getTexture(), 0, 0, PlayerEntity.FRAME_WIDTH, PlayerEntity.FRAME_HEIGHT)
	
	/**
	 * Set bounding box size for the player
	 * @param widthReduce:Float  Width reduced in percentage
	 * @param heightReduce:Float Height reduced in percentage
	 */
	def setBoundingSize(widthReduce:Float, heightReduce:Float){
		val width = PlayerEntity.FRAME_WIDTH * (1.0f - widthReduce)
		val height = PlayerEntity.FRAME_HEIGHT * (1.0f - heightReduce)

		if(width == 0 || height == 0){
			Gdx.app.debug(PlayerEntity.TAG, s"Width and Height are 0! $width:$height")
		}

		val minX = nextPlayerPosition.x / MapManager.UNIT_SCALE
		val minY = nextPlayerPosition.y / MapManager.UNIT_SCALE

		boundingBox.set(minX, minY, width, height)
	}

	/**
	 * Move player
	 */
	def move(){
		currentPlayerPosition.x = nextPlayerPosition.x
		currentPlayerPosition.y = nextPlayerPosition.y
		frameSprite.setX(currentPlayerPosition.x)
		frameSprite.setY(currentPlayerPosition.y)
	}
}

object PlayerEntity{

	private val TAG:String = PlayerEntity.getClass().getSimpleName()

	val defaultSpritePatch:String = "sprites/characters/Warrior.png"

	val FRAME_WIDTH = 16
	val FRAME_HEIGHT = 16

	AssetsManager.loadTextureAsset(PlayerEntity.defaultSpritePatch)
	val texture:Option[Texture] = AssetsManager.getTextureAsset(PlayerEntity.defaultSpritePatch)
	val walkDownFrames = new Array[TextureRegion](4)
	val walkLeftFrames = new Array[TextureRegion](4)
	val walkRightFrames = new Array[TextureRegion](4)
	val walkUpFrames = new Array[TextureRegion](4)
	texture match{
		case Some(tex) =>
			for(x <- 0 to 3; val textureFrames:scala.Array[scala.Array[TextureRegion]] = TextureRegion.split(tex, PlayerEntity.FRAME_WIDTH, PlayerEntity.FRAME_HEIGHT)){
				for(y <- 0 to 3){
					x match{
						case 0 => walkDownFrames.insert(y, textureFrames(x)(y))
						case 1 => walkLeftFrames.insert(y, textureFrames(x)(y))
						case 2 => walkRightFrames.insert(y, textureFrames(x)(y))
						case 3 => walkUpFrames.insert(y, textureFrames(x)(y))
					}
				}
			}
		case None => 
	}
	val walkDownAnimation = new Animation[TextureRegion](0.25f, walkDownFrames, Animation.PlayMode.LOOP)
	val walkLeftAnimation = new Animation[TextureRegion](0.25f, walkLeftFrames, Animation.PlayMode.LOOP)
	val walkRightAnimation = new Animation[TextureRegion](0.25f, walkRightFrames, Animation.PlayMode.LOOP)
	val walkUpAnimation = new Animation[TextureRegion](0.25f, walkUpFrames, Animation.PlayMode.LOOP)
}
