/*package my.game.pkg.entity.utils

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import my.game.pkg.entity.{MovingNPC, NPC, PlayerEntity}
import my.game.pkg.map.MapManager

//NPCs for Town
class TownNPCs extends MapNPCs {

	//record boolean value for one time update and move for non-moving NPC
	var firstMove = true
	var firstUpdate = true

	//NPCs for TOWN map
	val townMovingNPC1 = MovingNPC(PlayerEntity.spritePatchRogue, MapManager.TOWN, 17, 9, 20, 15)
	val townMovingNPC2 = MovingNPC(PlayerEntity.spritePatchEngineer, MapManager.TOWN, 10.8568f, 9.8568f, 8.5119f, 3.3652f)
	val townNPC1 = NPC(PlayerEntity.spritePatchWarrior, MapManager.TOWN, 11.6835f, 23.3770f, Direction.DOWN)
	val townNPC2 = NPC(PlayerEntity.spritePatchPaladin, MapManager.TOWN, 4.0811f, 26.3357f, Direction.DOWN)

	def initNPCs(): Unit ={
		townMovingNPC1.init(new Vector2(townMovingNPC1.positionX, townMovingNPC1.positionY))
		townMovingNPC2.init(new Vector2(townMovingNPC2.positionX, townMovingNPC2.positionY))
		townNPC1.init(new Vector2(townNPC1.positionX, townNPC1.positionY))
		townNPC2.init(new Vector2(townNPC2.positionX, townNPC2.positionY))
	}

	def updateMovingNPCs(delta:Float): Unit ={
		townMovingNPC1.update(delta)
		townMovingNPC2.update(delta)
		if(firstUpdate) {
			firstUpdate = false
			townNPC1.update(delta, State.IDLE)
			townNPC2.update(delta, State.IDLE)
		}
	}

	def moveNPCs(): Unit ={
		townMovingNPC1.move()
		townMovingNPC2.move()
		if(firstMove) {
			firstMove = false
			townNPC1.move()
			townNPC2.move()
		}
	}

	def drawNPCs(batch: Batch): Unit ={
		batch.draw(townMovingNPC1.currentFrame, townMovingNPC1.frameSprite.getX, townMovingNPC1.frameSprite.getY, 1,1)
		batch.draw(townMovingNPC2.currentFrame, townMovingNPC2.frameSprite.getX, townMovingNPC2.frameSprite.getY, 1,1)
		batch.draw(townNPC1.currentFrame, townNPC1.frameSprite.getX, townNPC1.frameSprite.getY, 1,1)
		batch.draw(townNPC2.currentFrame, townNPC2.frameSprite.getX, townNPC2.frameSprite.getY, 1,1)
	}

	def disposeNPCs(): Unit ={
		townMovingNPC1.dispose()
		townMovingNPC2.dispose()
		townNPC1.dispose()
		townNPC2.dispose()
	}

  }

object TownNPCs{
	def apply():TownNPCs = new TownNPCs
}
*/