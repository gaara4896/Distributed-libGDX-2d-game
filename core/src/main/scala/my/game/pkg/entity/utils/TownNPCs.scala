package my.game.pkg.entity.utils

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import my.game.pkg.Distributedlibgdx2dgame
import my.game.pkg.entity.{MovingNPC, NPC, PlayerEntity}
import my.game.pkg.map.MapManager

//NPCs for Town
class TownNPCs extends MapNPCs {

    //record boolean value for one time update and move for non-moving NPC
    var firstMove = true
    var firstUpdate = true

    //NPCs for TOWN map
    val TownMovingNPC1 = MovingNPC(PlayerEntity.spritePatchRogue, MapManager.TOWN, 17, 9, 20, 15)
    val TownMovingNPC2 = MovingNPC(PlayerEntity.spritePatchEngineer, MapManager.TOWN, 10.8568f, 9.8568f, 8.5119f, 3.3652f)
    val TownNPC1 = NPC(PlayerEntity.spritePatchWarrior, MapManager.TOWN, 11.6835f, 23.3770f, Direction.DOWN)
    val TownNPC2 = NPC(PlayerEntity.spritePatchPaladin, MapManager.TOWN, 4.0811f, 26.3357f, Direction.DOWN)

    def initNPCs(): Unit ={
        TownMovingNPC1.init(new Vector2(TownMovingNPC1.positionX, TownMovingNPC1.positionY))
        TownMovingNPC2.init(new Vector2(TownMovingNPC2.positionX, TownMovingNPC2.positionY))
        TownNPC1.init(new Vector2(TownNPC1.positionX, TownNPC1.positionY))
        TownNPC2.init(new Vector2(TownNPC2.positionX, TownNPC2.positionY))
    }

    def updateMovingNPCs(delta:Float): Unit ={
        TownMovingNPC1.update(delta)
        TownMovingNPC2.update(delta)
        if(firstUpdate) {
            firstUpdate = false
            TownNPC1.update(delta, State.IDLE)
            TownNPC2.update(delta, State.IDLE)
        }
    }

    def moveNPCs(game : Distributedlibgdx2dgame, mapName : String): Unit ={
        TownMovingNPC1.move(game, mapName)
        TownMovingNPC2.move(game, mapName)
        if(firstMove) {
            firstMove = false
            TownNPC1.move(game, mapName)
            TownNPC2.move(game, mapName)
        }
    }

    def drawNPCs(batch: Batch): Unit ={
        batch.draw(TownMovingNPC1.currentFrame, TownMovingNPC1.frameSprite.getX, TownMovingNPC1.frameSprite.getY, 1,1)
        batch.draw(TownMovingNPC2.currentFrame, TownMovingNPC2.frameSprite.getX, TownMovingNPC2.frameSprite.getY, 1,1)
        batch.draw(TownNPC1.currentFrame, TownNPC1.frameSprite.getX, TownNPC1.frameSprite.getY, 1,1)
        batch.draw(TownNPC2.currentFrame, TownNPC2.frameSprite.getX, TownNPC2.frameSprite.getY, 1,1)
    }

    def disposeNPCs(): Unit ={
        TownMovingNPC1.dispose()
        TownMovingNPC2.dispose()
        TownNPC1.dispose()
        TownNPC2.dispose()
    }

  }

object TownNPCs{
    def apply():TownNPCs = new TownNPCs
}
