/*package my.game.pkg.entity.utils

import com.badlogic.gdx.graphics.g2d.Batch
import my.game.pkg.map.MapManager

//base class for different map NPCs class
abstract class MapNPCs {
	def initNPCs()
	def updateNPCs(delta : Float)
	def moveNPCs()
	def drawNPCs(batch : Batch)
}

object MapNPCs{
	def apply(mapName : String): MapNPCs = {
		mapName match{
			case MapManager.TOWN => return new TownNPCs
			case MapManager.TOP_WORLD =>  return new TopWorldNPCs
			case MapManager.CASTLE_OF_DOOM => return new CastleOfDoomNPCs
		}
		return new TownNPCs
	}
}
*/