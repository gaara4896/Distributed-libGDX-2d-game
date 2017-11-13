package my.game.pkg.entity.npc

import com.badlogic.gdx.graphics.g2d.Batch
import my.game.pkg.map.MapManager

abstract class MapNPCs{

	/**
	 * Initialize the NPCs position
	 */
	def init()

	/**
	 * Update NPCs
	 * @param delta:Float Delta time of the frame
	 */
	def update(delta:Float)

	/**
	 * Draw NPCs to the screen
	 * @param batch:Batch Batch of the screen
	 */
	def draw(batch:Batch)
}

object MapNPCs{

	/**
	 * Apply method for creating MapNPCs
	 * @param  mapName:String Map name of the NPCs to be created
	 * @return MapNPCs New instance of MapNPCs
	 */
	def apply(mapName:String):MapNPCs = {
		mapName match{
			case MapManager.TOWN => TownNPCs()
			case MapManager.TOP_WORLD =>  TopWorldNPCs()
			case MapManager.CASTLE_OF_DOOM => CastleOfDoomNPCs()
			case _ => TownNPCs()
		}
	}
}
