package my.game.pkg.entity.npc

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2

import my.game.pkg.entity.NPC
import my.game.pkg.entity.utils.{Direction, Job}

class CastleOfDoomNPCs extends MapNPCs {

	val castleNPC1 = NPC(Job.ENGINEER, Direction.RIGHT)
	val castleNPC2 = NPC(Job.ROGUE, Direction.LEFT)
	val castleNPC3 = NPC(Job.PALADIN, Direction.RIGHT)
	val castleNPC4 = NPC(Job.ENGINEER, Direction.LEFT)
	val castleNPC5 = NPC(Job.ROGUE, Direction.RIGHT)
	val castleNPC6 = NPC(Job.WARRIOR, Direction.LEFT)
	val castleNPC7 = NPC(Job.ROGUE, Direction.RIGHT)

	/**
	 * Initialize NPCs position
	 */
	def init(){
		castleNPC1.init(new Vector2(7.2145f, 4.0440f))
		castleNPC2.init(new Vector2(24.0559f, 4.0440f))
		castleNPC3.init(new Vector2(7.2145f, 10.0111f))
		castleNPC4.init(new Vector2(24.0559f, 10.0111f))
		castleNPC5.init(new Vector2(7.2145f, 16.0407f))
		castleNPC6.init(new Vector2(24.0559f, 16.0407f))
		castleNPC7.init(new Vector2(2.5f, 75.5f))
	}

	/**
	 * Update NPCs 
	 * @param delta:Float Delta time of the frame
	 */
	def update(delta:Float){ }

	/**
	 * Draw NPCs to the screen
	 * @param batch:Batch Batch of the screen
	 */
	def draw(batch:Batch){
		batch.draw(castleNPC1.currentFrame, castleNPC1.frameSprite.getX, castleNPC1.frameSprite.getY, 1, 1)
		batch.draw(castleNPC2.currentFrame, castleNPC2.frameSprite.getX, castleNPC2.frameSprite.getY, 1, 1)
		batch.draw(castleNPC3.currentFrame, castleNPC3.frameSprite.getX, castleNPC3.frameSprite.getY, 1, 1)
		batch.draw(castleNPC4.currentFrame, castleNPC4.frameSprite.getX, castleNPC4.frameSprite.getY, 1, 1)
		batch.draw(castleNPC5.currentFrame, castleNPC5.frameSprite.getX, castleNPC5.frameSprite.getY, 1, 1)
		batch.draw(castleNPC6.currentFrame, castleNPC6.frameSprite.getX, castleNPC6.frameSprite.getY, 1, 1)
		batch.draw(castleNPC7.currentFrame, castleNPC7.frameSprite.getX, castleNPC7.frameSprite.getY, 1, 1)
	}

}

object CastleOfDoomNPCs{

	/**
	 * Apply method for creating CastleOfDoomNPCs
	 * @return CastlrOfDoomNPCs New instance of CastleOfDoomNPCs
	 */
	def apply():CastleOfDoomNPCs = new CastleOfDoomNPCs
}
