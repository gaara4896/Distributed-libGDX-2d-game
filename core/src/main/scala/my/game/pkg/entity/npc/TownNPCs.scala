package my.game.pkg.entity.npc

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2

import my.game.pkg.entity.{MovingNPC, NPC}
import my.game.pkg.entity.utils.{Direction, Job}

class TownNPCs extends MapNPCs {

	val townMovingNPC1 = MovingNPC(Job.ROGUE, 11, 3)
	val townMovingNPC2 = MovingNPC(Job.ENGINEER, 1f, 5.1467f)
	val townNPC1 = NPC(Job.WARRIOR, Direction.DOWN)
	val townNPC2 = NPC(Job.PALADIN, Direction.DOWN)

	/**
	 * Initialize NPCs position
	 */
	def init(){
		townMovingNPC1.init(new Vector2(8, 20))
		townMovingNPC2.init(new Vector2(9.7f, 8f))
		townNPC1.init(new Vector2(11.6835f, 23.3770f))
		townNPC2.init(new Vector2(4.0811f, 26.3357f))
	}

	/**
	 * Update NPCs 
	 * @param delta:Float Delta time of the frame
	 */
	def update(delta:Float){
		townMovingNPC1.update(delta)
		townMovingNPC2.update(delta)
	}

	/**
	 * Draw NPCs to the screen
	 * @param batch:Batch Batch of the screen
	 */
	def draw(batch: Batch): Unit ={
		batch.draw(townMovingNPC1.currentFrame, townMovingNPC1.frameSprite.getX, townMovingNPC1.frameSprite.getY, 1, 1)
		batch.draw(townMovingNPC2.currentFrame, townMovingNPC2.frameSprite.getX, townMovingNPC2.frameSprite.getY, 1, 1)
		batch.draw(townNPC1.currentFrame, townNPC1.frameSprite.getX, townNPC1.frameSprite.getY, 1, 1)
		batch.draw(townNPC2.currentFrame, townNPC2.frameSprite.getX, townNPC2.frameSprite.getY, 1, 1)
	}

  }

object TownNPCs{

	/**
	 * Apply method for creating TownNPCs
	 * @return TownNPCs New instance of TownNPCs
	 */
	def apply():TownNPCs = new TownNPCs
}
