/*package my.game.pkg.entity.utils

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import my.game.pkg.entity.{NPC, PlayerEntity}
import my.game.pkg.map.MapManager

//NPCs for Castle Of Doom
class CastleOfDoomNPCs extends MapNPCs {

	//record boolean value for one time update and move for non-moving NPC
	var firstMove = true
	var firstUpdate = true

	//NPCs for CASTLE OF DOOM
	val CastleNPC1 = NPC(PlayerEntity.spritePatchEngineer, MapManager.CASTLE_OF_DOOM, 7.2145f, 4.0440f, Direction.RIGHT)
	val CastleNPC2 = NPC(PlayerEntity.spritePatchRogue, MapManager.CASTLE_OF_DOOM, 24.0559f, 4.0440f, Direction.LEFT)
	val CastleNPC3 = NPC(PlayerEntity.spritePatchPaladin, MapManager.CASTLE_OF_DOOM, 7.2145f, 10.0111f, Direction.RIGHT)
	val CastleNPC4 = NPC(PlayerEntity.spritePatchEngineer, MapManager.CASTLE_OF_DOOM, 24.0559f, 10.0111f, Direction.LEFT)
	val CastleNPC5 = NPC(PlayerEntity.spritePatchRogue, MapManager.CASTLE_OF_DOOM, 7.2145f, 16.0407f, Direction.RIGHT)
	val CastleNPC6 = NPC(PlayerEntity.spritePatchWarrior, MapManager.CASTLE_OF_DOOM, 24.0559f, 16.0407f, Direction.LEFT)

	def initNPCs(): Unit ={
		CastleNPC1.init(new Vector2(CastleNPC1.positionX, CastleNPC1.positionY))
		CastleNPC2.init(new Vector2(CastleNPC2.positionX, CastleNPC2.positionY))
		CastleNPC3.init(new Vector2(CastleNPC3.positionX, CastleNPC3.positionY))
		CastleNPC4.init(new Vector2(CastleNPC4.positionX, CastleNPC4.positionY))
		CastleNPC5.init(new Vector2(CastleNPC5.positionX, CastleNPC5.positionY))
		CastleNPC6.init(new Vector2(CastleNPC6.positionX, CastleNPC6.positionY))

	}

	def updateMovingNPCs(delta:Float): Unit ={
		if(firstUpdate) {
			firstUpdate = false
			CastleNPC1.update(delta, State.IDLE)
			CastleNPC2.update(delta, State.IDLE)
			CastleNPC3.update(delta, State.IDLE)
			CastleNPC4.update(delta, State.IDLE)
			CastleNPC5.update(delta, State.IDLE)
			CastleNPC6.update(delta, State.IDLE)
		}
	}

	def moveNPCs(): Unit ={
		if(firstMove) {
			firstMove = false
			CastleNPC1.move()
			CastleNPC2.move()
			CastleNPC3.move()
			CastleNPC4.move()
			CastleNPC5.move()
			CastleNPC6.move()
		}
	}

	def drawNPCs(batch: Batch): Unit ={
		batch.draw(CastleNPC1.currentFrame, CastleNPC1.frameSprite.getX, CastleNPC1.frameSprite.getY, 1, 1)
		batch.draw(CastleNPC2.currentFrame, CastleNPC2.frameSprite.getX, CastleNPC2.frameSprite.getY, 1, 1)
		batch.draw(CastleNPC3.currentFrame, CastleNPC3.frameSprite.getX, CastleNPC3.frameSprite.getY, 1, 1)
		batch.draw(CastleNPC4.currentFrame, CastleNPC4.frameSprite.getX, CastleNPC4.frameSprite.getY, 1, 1)
		batch.draw(CastleNPC5.currentFrame, CastleNPC5.frameSprite.getX, CastleNPC5.frameSprite.getY, 1, 1)
		batch.draw(CastleNPC6.currentFrame, CastleNPC6.frameSprite.getX, CastleNPC6.frameSprite.getY, 1, 1)
	}

	def disposeNPCs(): Unit = {
		CastleNPC1.dispose()
		CastleNPC2.dispose()
		CastleNPC3.dispose()
		CastleNPC4.dispose()
		CastleNPC5.dispose()
		CastleNPC6.dispose()
	}

}

object CastleOfDoomNPCs{
	def apply():CastleOfDoomNPCs = new CastleOfDoomNPCs
}
*/