package my.game.pkg.entity

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.{Sprite, TextureRegion}
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array

import my.game.pkg.assets.AssetsManager
import my.game.pkg.entity.utils.Direction
import my.game.pkg.entity.utils.Direction._

class NPC (spritePatch:String, direction:Direction) extends Entity{

	currentDirection = direction
	AssetsManager.loadTextureAsset(spritePatch)
	val texture = AssetsManager.getTextureAsset(spritePatch)
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
	var currentFrame:TextureRegion = 
		if(currentDirection == Direction.DOWN) walkDownFrames.get(0) 
		else if(currentDirection == Direction.LEFT) walkLeftFrames.get(0)
		else if(currentDirection == Direction.RIGHT) walkRightFrames.get(0)
		else walkDownFrames.get(0)
	val frameSprite:Sprite = new Sprite(currentFrame.getTexture(), 0, 0, Entity.FRAME_WIDTH, Entity.FRAME_HEIGHT)

	/**
	 * Initialize NPC position
	 * @param  initPosition:Vector2 Position of NPC to be initialize
	 */
	def init(initPosition:Vector2){
		position.x = initPosition.x
		position.y = initPosition.y
		frameSprite.setX(initPosition.x)
		frameSprite.setY(initPosition.y) 
	}

}

object NPC{
	/**
	 * Apply method for creating NPC
	 * @return NPC New instance of NPC
	 */
	def apply(spritePatch:String, direction:Direction):NPC = new NPC(spritePatch, direction)

	private val TAG:String = NPC.getClass().getSimpleName()
}
