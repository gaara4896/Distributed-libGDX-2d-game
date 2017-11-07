package my.game.pkg.map

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.maps.MapLayer
import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math._

import java.util.Hashtable
import scala.collection.JavaConverters._

import my.game.pkg.assets.AssetsManager

class MapManager{
	val playerStart = new Vector2(0, 0)
	val mapTable = new Hashtable[String, String]()

	mapTable.put(MapManager.TOP_WORLD, "maps/topworld.tmx")
	mapTable.put(MapManager.TOWN, "maps/town.tmx")
	mapTable.put(MapManager.CASTLE_OF_DOOM, "maps/castle_of_doom.tmx")

	val playerStartLocationTable = new Hashtable[String, Vector2]()
	playerStartLocationTable.put(MapManager.TOP_WORLD, playerStart.cpy())
	playerStartLocationTable.put(MapManager.TOWN, playerStart.cpy())
	playerStartLocationTable.put(MapManager.CASTLE_OF_DOOM, playerStart.cpy())

	val playerStartPositionRect = new Vector2(0, 0)
	val closestPlayerStartPosition = new Vector2(0,0)
	val convertedUnits = new Vector2(0,0)

	var currentMap:TiledMap = null
    var currentMapName:String = null
    var collisionLayer:MapLayer = null
    var portalLayer:MapLayer = null
    var spawnsLayer:MapLayer = null

    /** 
     * laoadMap execute when used to load a specific map
     * @param mapName:String Name of the map
     */
    def loadMap(mapName:String){
    	playerStart.set(0, 0)

    	val mapFullPath = Option(mapTable.get(mapName))

    	mapFullPath match{
    		case None => Gdx.app.debug(MapManager.TAG, "Map is invalid")
    			return
            case Some(x) => 
                if(currentMap != null){
                    currentMap.dispose()
                }
                AssetsManager.loadMapAsset(x)
                AssetsManager.getMapAsset(x) match{
                    case Some(y) => currentMap = y.asInstanceOf[TiledMap]
                        currentMapName = mapName
                }
    	}

    	collisionLayer = currentMap.getLayers().get(MapManager.MAP_COLLISION_LAYER)
    	if(collisionLayer == null){
    		Gdx.app.debug(MapManager.TAG, "No collision layer!")
    	}

    	portalLayer = currentMap.getLayers().get(MapManager.MAP_PORTAL_LAYER)
    	if(portalLayer == null){
    		Gdx.app.debug(MapManager.TAG, "No portal layer!")
    	}

    	spawnsLayer = currentMap.getLayers().get(MapManager.MAP_SPAWNS_LAYER)
    	if(spawnsLayer == null){
    		Gdx.app.debug(MapManager.TAG, "No spawn layer!")
    	} else {
    		var start:Vector2 = playerStartLocationTable.get(currentMapName)
    		if(start.isZero()){
    			setClosestStartPosition(playerStart)
    			start = playerStartLocationTable.get(currentMapName)
    		}
    		playerStart.set(start.x, start.y)
    	}

    	Gdx.app.debug(MapManager.TAG, s"Player start: (${playerStart.x}, ${playerStart.y})")
    }

    /**
     * Get current map Tiled for rendering
     * @return TiledMap Tiled of the Map
     */
    def getCurrentMap():TiledMap = {
    	if(currentMap == null){
    		currentMapName = MapManager.TOWN
    		loadMap(currentMapName)
    	}
    	currentMap
    }

    /**
     * Get player start position of the map
     * @return Vector2 Position of the player to start
     */
    def getPlayerStartUnitScaled:Vector2 = {
    	val _playerStart = playerStart.cpy()
    	_playerStart.set(playerStart.x * MapManager.UNIT_SCALE, playerStart.y * MapManager.UNIT_SCALE)
    	_playerStart
    }

    /**
     * Set player start position to the closest spawm point to previous map
     * @param position:Vector2 Position of the player closest start position
     */
    def setClosestStartPosition(position:Vector2){
    	Gdx.app.debug(MapManager.TAG, s"setClosestStartPosition INPUT: (${position.x}, ${position.y})$currentMapName")

    	playerStartPositionRect.set(0, 0)
    	closestPlayerStartPosition.set(0, 0)
    	var shortestDistance:Float = 0f

    	for(x <- spawnsLayer.getObjects().iterator().asScala){
    		if(x.getName().equalsIgnoreCase(MapManager.PLAYER_START)){
    			x.asInstanceOf[RectangleMapObject].getRectangle().getPosition(playerStartPositionRect)
    			var distance = position.dst2(playerStartPositionRect)

    			Gdx.app.debug(MapManager.TAG, s"distance: $distance for $currentMapName")

    			if(distance < shortestDistance || shortestDistance == 0){
    				closestPlayerStartPosition.set(playerStartPositionRect)
    				shortestDistance = distance
    				Gdx.app.debug(MapManager.TAG, s"closest START is: (${closestPlayerStartPosition.x}, ${closestPlayerStartPosition.y})$currentMapName")
    			}
    		}
    	}
    	playerStartLocationTable.put(currentMapName, closestPlayerStartPosition.cpy())
    }

    /**
     * Set player start position to the closest spawm point in Scaled Unit
     * @param position:Vector2 Position of the player to the closest start position
     */
    def setClosestStartPositionFromScaledUnits(position:Vector2){
    	if(MapManager.UNIT_SCALE <= 0) return

    	convertedUnits.set(position.x/MapManager.UNIT_SCALE, position.y/MapManager.UNIT_SCALE)
    	setClosestStartPosition(convertedUnits)
    }
}

object MapManager{

	/**
	 * Apply method for creating MapManager
     * @return MapManager New instance of MapManager
	 */
	def apply():MapManager = new MapManager

	private val TAG:String = MapManager.getClass.getSimpleName()

	private val TOP_WORLD:String = "TOP_WORLD"
	private val TOWN:String = "TOWN"
	private val CASTLE_OF_DOOM:String = "CASTLE_OF_DOOM"
	private val MAP_COLLISION_LAYER:String = "MAP_COLLISION_LAYER"
	private val MAP_SPAWNS_LAYER:String = "MAP_SPAWNS_LAYER"
	private val MAP_PORTAL_LAYER:String = "MAP_PORTAL_LAYER"
	private val PLAYER_START:String = "PLAYER_START"

	val UNIT_SCALE:Float = 1/16f

}