/*package my.game.pkg.entity.utils

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import my.game.pkg.entity.{MovingNPC, PlayerEntity}
import my.game.pkg.map.MapManager

//NPCs for Top World
class TopWorldNPCs extends MapNPCs {

	//record boolean value for one time update and move for non-moving NPC
	var firstMove = true
	var firstUpdate = true

	//NPCs for TOP WORLD
	val topWorldMovingNPC1 = MovingNPC(PlayerEntity.spritePatchWarrior, MapManager.TOP_WORLD, 48.0329f, 47.5329f, 68.2196f, 62.2196f)
	val topWorldMovingNPC2 = MovingNPC(PlayerEntity.spritePatchWarrior, MapManager.TOP_WORLD, 17.6086f, 16.6086f, 23.1235f, 18.5943f)

	def initNPCs(): Unit ={
		topWorldMovingNPC1.init(new Vector2(topWorldMovingNPC1.positionX, topWorldMovingNPC1.positionY))
		topWorldMovingNPC2.init(new Vector2(topWorldMovingNPC2.positionX, topWorldMovingNPC2.positionY))
	}

	def updateMovingNPCs(delta:Float): Unit ={
		topWorldMovingNPC1.update(delta)
		topWorldMovingNPC2.update(delta)
		if(firstUpdate) {
			firstUpdate = false
		}
	}

	def moveNPCs(): Unit ={
		topWorldMovingNPC1.move()
		topWorldMovingNPC2.move()
		if(firstMove) {
			firstMove = false
		}
	}

	def drawNPCs(batch: Batch): Unit ={
		batch.draw(topWorldMovingNPC1.currentFrame, topWorldMovingNPC1.frameSprite.getX, topWorldMovingNPC1.frameSprite.getY, 1, 1)
		batch.draw(topWorldMovingNPC2.currentFrame, topWorldMovingNPC2.frameSprite.getX, topWorldMovingNPC2.frameSprite.getY, 1, 1)
	}

	def disposeNPCs(): Unit = {
		topWorldMovingNPC1.dispose()
		topWorldMovingNPC2.dispose()
	}

}

object TopWorldNPCs{
	def apply():TopWorldNPCs = new TopWorldNPCs
}
*/