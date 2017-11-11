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
	val TopWorldMovingNPC1 = MovingNPC(PlayerEntity.spritePatchWarrior, MapManager.TOP_WORLD, 48.0329f, 47.5329f, 68.2196f, 62.2196f)
	val TopWorldMovingNPC2 = MovingNPC(PlayerEntity.spritePatchWarrior, MapManager.TOP_WORLD, 17.6086f, 16.6086f, 23.1235f, 18.5943f)

	def initNPCs(): Unit ={
		TopWorldMovingNPC1.init(new Vector2(TopWorldMovingNPC1.positionX, TopWorldMovingNPC1.positionY))
		TopWorldMovingNPC2.init(new Vector2(TopWorldMovingNPC2.positionX, TopWorldMovingNPC2.positionY))
	}

	def updateMovingNPCs(delta:Float): Unit ={
		TopWorldMovingNPC1.update(delta)
		TopWorldMovingNPC2.update(delta)
		if(firstUpdate) {
			firstUpdate = false
		}
	}

	def moveNPCs(): Unit ={
		TopWorldMovingNPC1.move()
		TopWorldMovingNPC2.move()
		if(firstMove) {
			firstMove = false
		}
	}

	def drawNPCs(batch: Batch): Unit ={
		batch.draw(TopWorldMovingNPC1.currentFrame, TopWorldMovingNPC1.frameSprite.getX, TopWorldMovingNPC1.frameSprite.getY, 1, 1)
		batch.draw(TopWorldMovingNPC2.currentFrame, TopWorldMovingNPC2.frameSprite.getX, TopWorldMovingNPC2.frameSprite.getY, 1, 1)
	}

	def disposeNPCs(): Unit = {
		TopWorldMovingNPC1.dispose()
		TopWorldMovingNPC2.dispose()
	}

}

object TopWorldNPCs{
	def apply():TopWorldNPCs = new TopWorldNPCs
}
*/