package my.game.pkg.entity

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{Animation, TextureRegion}
import com.badlogic.gdx.utils.Array

import my.game.pkg.assets.AssetsManager
import my.game.pkg.entity.utils.{Direction, Job}
import my.game.pkg.entity.utils.Direction._
import my.game.pkg.entity.utils.Job._

import scala.collection.mutable.Map

class EntitySprite(val spritePath:String){
	AssetsManager.loadTextureAsset(spritePath)
	val texture:Option[Texture] = AssetsManager.getTextureAsset(spritePath)
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
	private val entitySpritePatch:Map[Job, EntitySprite] = Map()
	private val spriteFileName:Map[Job, String] = Map(Job.WARRIOR -> "Warrior.png", Job.ROGUE -> "Rogue.png", 
		Job.ENGINEER -> "Engineer.png", Job.PALADIN -> "Paladin.png", Job.MAGE -> "Mage.png")

	private def apply(job:Job):EntitySprite = {
		var spritePatch = entitySpritePatch.get(job)
		spritePatch match{
			case Some(_) =>
			case None => 
				entitySpritePatch += (job -> new EntitySprite(s"sprites/characters/${spriteFileName(job)}"))
				spritePatch = entitySpritePatch.get(job)
		}
		spritePatch.get
	}

	def getFirstTexture(job:Job, direction:Direction):TextureRegion = {
		val spritePatch = EntitySprite(job)
		var firstFrame:Option[TextureRegion] = None
		direction match {
			case Direction.LEFT => firstFrame = Option(spritePatch.walkLeftFrames.get(0))
			case Direction.RIGHT => firstFrame = Option(spritePatch.walkRightFrames.get(0))
			case Direction.UP => firstFrame = Option(spritePatch.walkUpFrames.get(0))
			case Direction.DOWN =>  firstFrame = Option(spritePatch.walkDownFrames.get(0))
		}
		firstFrame.get
	}

	def getAnimation(job:Job, direction:Direction):Animation[TextureRegion] = {
		val spritePatch = EntitySprite(job)
		var animation:Option[Animation[TextureRegion]] = None
		direction match{
			case Direction.LEFT => animation = Option(spritePatch.walkLeftAnimation)
			case Direction.RIGHT => animation = Option(spritePatch.walkRightAnimation)
			case Direction.UP => animation = Option(spritePatch.walkUpAnimation)
			case Direction.DOWN => animation = Option(spritePatch.walkDownAnimation)
		}
		animation.get
	}

	def dispose(){
		entitySpritePatch.keys.foreach{x =>
			AssetsManager.unloadAsset(s"sprites/characters/${spriteFileName(x)}")
		}
	}
}