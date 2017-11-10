package my.game.pkg.entity

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{Animation, Sprite, TextureRegion}
import com.badlogic.gdx.utils.Array
import my.game.pkg.assets.AssetsManager
import my.game.pkg.entity.utils.Direction._

abstract class PlayerEntity {
	var currentDirection:Direction
	var frameTime:Float
	var currentFrame:TextureRegion = PlayerEntity.walkDownFrames.get(0)
	val frameSprite:Sprite

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
	}
	val walkDownAnimation = new Animation[TextureRegion](0.25f, walkDownFrames, Animation.PlayMode.LOOP)
	val walkLeftAnimation = new Animation[TextureRegion](0.25f, walkLeftFrames, Animation.PlayMode.LOOP)
	val walkRightAnimation = new Animation[TextureRegion](0.25f, walkRightFrames, Animation.PlayMode.LOOP)
	val walkUpAnimation = new Animation[TextureRegion](0.25f, walkUpFrames, Animation.PlayMode.LOOP)
}
