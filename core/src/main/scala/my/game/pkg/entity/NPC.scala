package my.game.pkg.entity

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{Sprite, TextureRegion}
import com.badlogic.gdx.math.Vector2

import my.game.pkg.assets.AssetsManager
import my.game.pkg.entity.utils.Direction
import my.game.pkg.entity.utils.Direction._
import my.game.pkg.entity.utils.State

//positionX and position Y are for the coordinate of the NPC
class NPC (spritePatch:String, direction:Direction) extends Entity{

	currentDirection = direction
	AssetsManager.loadTextureAsset(spritePatch)
	val texture:Texture = AssetsManager.getTextureAsset(spritePatch).get
	var currentFrame:TextureRegion = 
		if(currentDirection == Direction.DOWN) TextureRegion.split(texture, Entity.FRAME_WIDTH, Entity.FRAME_HEIGHT)(0)(0) 
		else if(currentDirection == Direction.LEFT) TextureRegion.split(texture, Entity.FRAME_WIDTH, Entity.FRAME_HEIGHT)(1)(0)
		else if(currentDirection == Direction.RIGHT) TextureRegion.split(texture, Entity.FRAME_WIDTH, Entity.FRAME_HEIGHT)(2)(0)
		else TextureRegion.split(texture, Entity.FRAME_WIDTH, Entity.FRAME_HEIGHT)(3)(0)
	val frameSprite:Sprite = new Sprite(currentFrame.getTexture(), 0, 0, Entity.FRAME_WIDTH, Entity.FRAME_HEIGHT)

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
