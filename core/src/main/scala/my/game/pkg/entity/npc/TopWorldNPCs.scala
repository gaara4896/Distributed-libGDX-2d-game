package my.game.pkg.entity.npc

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2

import my.game.pkg.entity.{MovingNPC, Entity}

class TopWorldNPCs extends MapNPCs {

	var firstMove = true
	var firstUpdate = true

	val topWorldMovingNPC1 = MovingNPC(Entity.spritePatchWarrior, 0.5f, 5f)
	val topWorldMovingNPC2 = MovingNPC(Entity.spritePatchWarrior, 1f, 4.5292f)
	val topWorldMovingNPC3 = MovingNPC(Entity.spritePatchMage, 7.3f, 2f)
	val topWorldMovingNPC4 = MovingNPC(Entity.spritePatchPaladin, 22f, 9.5f)

	/**
	 * Initialize NPCs position
	 */
	def init(){
		topWorldMovingNPC1.init(new Vector2(47.1329f, 68.2196f))
		topWorldMovingNPC2.init(new Vector2(16.6086f, 23.1235f))
		topWorldMovingNPC3.init(new Vector2(45.7690f, 30.3483f))
		topWorldMovingNPC4.init(new Vector2(15.2256f, 34.9951f))
	}

	/**
	 * Update NPCs 
	 * @param delta:Float Delta time of the frame
	 */
	def update(delta:Float){
		topWorldMovingNPC1.update(delta)
		topWorldMovingNPC2.update(delta)
		topWorldMovingNPC3.update(delta)
		topWorldMovingNPC4.update(delta)
	}

	/**
	 * Draw NPCs to the screen
	 * @param batch:Batch Batch of the screen
	 */
	def draw(batch: Batch){
		batch.draw(topWorldMovingNPC1.currentFrame, topWorldMovingNPC1.frameSprite.getX, topWorldMovingNPC1.frameSprite.getY, 1, 1)
		batch.draw(topWorldMovingNPC2.currentFrame, topWorldMovingNPC2.frameSprite.getX, topWorldMovingNPC2.frameSprite.getY, 1, 1)
		batch.draw(topWorldMovingNPC3.currentFrame, topWorldMovingNPC3.frameSprite.getX, topWorldMovingNPC3.frameSprite.getY, 1, 1)
		batch.draw(topWorldMovingNPC4.currentFrame, topWorldMovingNPC4.frameSprite.getX, topWorldMovingNPC4.frameSprite.getY, 1, 1)
	}

}

object TopWorldNPCs{

	/**
	 * Apply method for creating TopWorldNPCs
	 * @return TopWorldNPCs New instance of TopWorldNPCs
	 */
	def apply():TopWorldNPCs = new TopWorldNPCs
}
