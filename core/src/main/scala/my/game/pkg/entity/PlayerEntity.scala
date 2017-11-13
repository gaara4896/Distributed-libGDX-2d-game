package my.game.pkg.entity

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{Animation, Sprite, TextureRegion}
import com.badlogic.gdx.math.{Rectangle, Vector2}
import com.badlogic.gdx.utils.Array

import my.game.pkg.assets.AssetsManager
import my.game.pkg.entity.utils.Direction
import my.game.pkg.map.MapManager

abstract class PlayerEntity(val patch:Int) extends Entity{

	val velocity = new Vector2(10f, 10f)
	var previousDirection = Direction.UP
	val nextPosition = new Vector2()
	val boundingBox = new Rectangle()

	val defaultSpritePatch:String = setupPatch(patch)
	println(defaultSpritePatch)

	AssetsManager.loadTextureAsset(defaultSpritePatch)
	val texture:Option[Texture] = AssetsManager.getTextureAsset(defaultSpritePatch)
	val walkDownFrames = new Array[TextureRegion](4)
	val walkLeftFrames = new Array[TextureRegion](4)
	val walkRightFrames = new Array[TextureRegion](4)
	val walkUpFrames = new Array[TextureRegion](4)
	texture match{
		case Some(tex) =>
			for(x <- 0 to 3; val textureFrames:scala.Array[scala.Array[TextureRegion]] = TextureRegion.split(tex, Entity.FRAME_WIDTH, Entity.FRAME_HEIGHT)){
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

	var currentFrame:TextureRegion = walkDownFrames.get(0)
	val frameSprite = new Sprite(currentFrame.getTexture(), 0, 0, Entity.FRAME_WIDTH, Entity.FRAME_HEIGHT)

	//set player's patch according to patch:Int
	def setupPatch(patch:Int): String ={
		patch match{
				case 0 => Entity.spritePatchWarrior
				case 1 => Entity.spritePatchRogue
				case 2 => Entity.spritePatchMage
				case 3 => Entity.spritePatchPaladin
				case 4 => Entity.spritePatchEngineer
				case _ => 
					println("Undeterministic")
					Entity.spritePatchWarrior
		}
	}

	/**
	 * Set bounding box size for the player
	 * @param widthReduce:Float  Width reduced in percentage
	 * @param heightReduce:Float Height reduced in percentage
	 */
	def setBoundingSize(widthReduce:Float, heightReduce:Float){
		val width = Entity.FRAME_WIDTH * (1.0f - widthReduce)
		val height = Entity.FRAME_HEIGHT * (1.0f - heightReduce)

		if(width == 0 || height == 0){
			Gdx.app.debug(PlayerEntity.TAG, s"Width and Height are 0! $width:$height")
		}

		val minX = nextPosition.x / MapManager.UNIT_SCALE
		val minY = nextPosition.y / MapManager.UNIT_SCALE

		boundingBox.set(minX, minY, width, height)
	}

	/**
	 * Move player
	 */
	def move(){
		position.x = nextPosition.x
		position.y = nextPosition.y
		frameSprite.setX(position.x)
		frameSprite.setY(position.y)
	}
}

object PlayerEntity{

	private val TAG:String = PlayerEntity.getClass().getSimpleName()

}
