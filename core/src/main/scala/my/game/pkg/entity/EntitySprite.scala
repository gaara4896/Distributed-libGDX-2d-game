package my.game.pkg.entity

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{Animation, TextureRegion}
import com.badlogic.gdx.utils.Array

import my.game.pkg.assets.AssetsManager
import my.game.pkg.entity.utils.Direction
import my.game.pkg.entity.utils.Direction._

import scala.collection.mutable.Map

class EntitySprite(val sprite:String){
	AssetsManager.loadTextureAsset(sprite)
	val texture:Option[Texture] = AssetsManager.getTextureAsset(sprite)
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
}

object EntitySprite{

	private val TAG:String = EntitySprite.getClass().getSimpleName()
	private val entitySpritePatch:Map[String, EntitySprite] = Map()

	private def apply(sprite:String):EntitySprite = {
		var spritePatch = entitySpritePatch.get(sprite)
		spritePatch match{
			case Some(_) =>
			case None => 
				entitySpritePatch += (sprite -> new EntitySprite(sprite))
				spritePatch = entitySpritePatch.get(sprite)
		}
		spritePatch.get
	}

	def getFirstTexture(sprite:String, direction:Direction):TextureRegion = {
		val spritePatch = EntitySprite(sprite)
		var firstFrame:Option[TextureRegion] = None
		direction match {
			case Direction.LEFT => firstFrame = Option(spritePatch.walkLeftFrames.get(0))
			case Direction.RIGHT => firstFrame = Option(spritePatch.walkRightFrames.get(0))
			case Direction.UP => firstFrame = Option(spritePatch.walkUpFrames.get(0))
			case Direction.DOWN =>  firstFrame = Option(spritePatch.walkDownFrames.get(0))
		}
		firstFrame.get
	}

	def getAnimation(sprite:String, direction:Direction):Animation[TextureRegion] = {
		val spritePatch = EntitySprite(sprite)
		var animation:Option[Animation[TextureRegion]] = None
		direction match{
			case Direction.LEFT => animation = Option(spritePatch.walkLeftAnimation)
			case Direction.RIGHT => animation = Option(spritePatch.walkRightAnimation)
			case Direction.UP => animation = Option(spritePatch.walkUpAnimation)
			case Direction.DOWN => animation = Option(spritePatch.walkDownAnimation)
		}
		animation.get
	}
}