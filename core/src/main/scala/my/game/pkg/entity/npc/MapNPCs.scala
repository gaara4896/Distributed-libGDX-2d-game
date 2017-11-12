package my.game.pkg.entity.npc

import com.badlogic.gdx.graphics.g2d.Batch
import my.game.pkg.map.MapManager

abstract class MapNPCs {
	def init()
	def update(delta : Float)
	def draw(batch : Batch)
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
