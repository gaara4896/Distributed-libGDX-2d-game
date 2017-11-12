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
	val castleNPC1 = NPC(PlayerEntity.spritePatchEngineer, 7.2145f, 4.0440f, Direction.RIGHT)
	val castleNPC2 = NPC(PlayerEntity.spritePatchRogue, 24.0559f, 4.0440f, Direction.LEFT)
	val castleNPC3 = NPC(PlayerEntity.spritePatchPaladin, 7.2145f, 10.0111f, Direction.RIGHT)
	val castleNPC4 = NPC(PlayerEntity.spritePatchEngineer, 24.0559f, 10.0111f, Direction.LEFT)
	val castleNPC5 = NPC(PlayerEntity.spritePatchRogue, 7.2145f, 16.0407f, Direction.RIGHT)
	val castleNPC6 = NPC(PlayerEntity.spritePatchWarrior, 24.0559f, 16.0407f, Direction.LEFT)

	def initNPCs(): Unit ={
		castleNPC1.init(new Vector2(castleNPC1.positionX, castleNPC1.positionY))
		castleNPC2.init(new Vector2(castleNPC2.positionX, castleNPC2.positionY))
		castleNPC3.init(new Vector2(castleNPC3.positionX, castleNPC3.positionY))
		castleNPC4.init(new Vector2(castleNPC4.positionX, castleNPC4.positionY))
		castleNPC5.init(new Vector2(castleNPC5.positionX, castleNPC5.positionY))
		castleNPC6.init(new Vector2(castleNPC6.positionX, castleNPC6.positionY))

	}

	def updateNPCs(delta:Float): Unit ={
		if(firstUpdate) {
			firstUpdate = false
			castleNPC1.update(delta, State.IDLE)
			castleNPC2.update(delta, State.IDLE)
			castleNPC3.update(delta, State.IDLE)
			castleNPC4.update(delta, State.IDLE)
			castleNPC5.update(delta, State.IDLE)
			castleNPC6.update(delta, State.IDLE)
		}
	}

	def moveNPCs(): Unit = {}

	def drawNPCs(batch: Batch): Unit ={
		batch.draw(castleNPC1.currentFrame, castleNPC1.frameSprite.getX, castleNPC1.frameSprite.getY, 1, 1)
		batch.draw(castleNPC2.currentFrame, castleNPC2.frameSprite.getX, castleNPC2.frameSprite.getY, 1, 1)
		batch.draw(castleNPC3.currentFrame, castleNPC3.frameSprite.getX, castleNPC3.frameSprite.getY, 1, 1)
		batch.draw(castleNPC4.currentFrame, castleNPC4.frameSprite.getX, castleNPC4.frameSprite.getY, 1, 1)
		batch.draw(castleNPC5.currentFrame, castleNPC5.frameSprite.getX, castleNPC5.frameSprite.getY, 1, 1)
		batch.draw(castleNPC6.currentFrame, castleNPC6.frameSprite.getX, castleNPC6.frameSprite.getY, 1, 1)
	}

	def disposeNPCs(): Unit = {
		castleNPC1.dispose()
		castleNPC2.dispose()
		castleNPC3.dispose()
		castleNPC4.dispose()
		castleNPC5.dispose()
		castleNPC6.dispose()
	}

}

object CastleOfDoomNPCs{
	def apply():CastleOfDoomNPCs = new CastleOfDoomNPCs
}
*/