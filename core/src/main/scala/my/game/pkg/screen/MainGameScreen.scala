package my.game.pkg.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.{GL20, OrthographicCamera}
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.{Rectangle, Vector2}
import com.badlogic.gdx.graphics.g2d.{BitmapFont, SpriteBatch}
import my.game.pkg.Distributedlibgdx2dgame
import my.game.pkg.map.MapManager
import my.game.pkg.controller.PlayerController
import my.game.pkg.entity.utils.{MapNPCs, TownNPCs}
import my.game.pkg.entity.{MovingNPC, NPC, Player, RemotePlayer}
import my.game.pkg.entity.utils.State

import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer

class MainGameScreen(val game:Distributedlibgdx2dgame) extends Screen{

	val controller = PlayerController(MainGameScreen.player)
	var currentPlayerFrame:TextureRegion = null
	var currentPlayerSprite = MainGameScreen.player.frameSprite
	val mapRenderer = new OrthogonalTiledMapRenderer(MainGameScreen.mapMgr.getCurrentMap(), MapManager.UNIT_SCALE)
	val camera = new OrthographicCamera()
	val font = new BitmapFont()
	val spriteBatch = new SpriteBatch()

	/**
	 * Execute when no screen is showed
	 */
	override def show{
		MainGameScreen.VIEWPORT.setupViewport(15, 15, Gdx.graphics.getWidth(), Gdx.graphics.getHeight())

		camera.setToOrtho(false, MainGameScreen.VIEWPORT.viewportWidth, MainGameScreen.VIEWPORT.viewportHeight)
		mapRenderer.setView(camera)

		Gdx.app.debug(MainGameScreen.TAG, s"UnitScale Value is: ${mapRenderer.getUnitScale()}")

		MainGameScreen.player.init(MainGameScreen.mapMgr.getPlayerStartUnitScaled)
		MainGameScreen.player.move(game, MainGameScreen.mapMgr.currentMapName)
		Gdx.input.setInputProcessor(controller)
	}

	/**
	 * Execute when hiding the screen
	 */
	override def hide{}

	/**
	 * Execute each frame to render the screen
	 * @param delta:Float Time delta of the frame
	 */
	override def render(delta:Float){
		Gdx.gl.glClearColor(0, 0, 0, 1)
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

		MainGameScreen.NPCs.updateMovingNPCs(delta)
		controller.update(delta, game)

		currentPlayerFrame = MainGameScreen.player.currentFrame

		if(MainGameScreen.player.state == State.WALKING){
			updatePortalLayerActivation(MainGameScreen.player.boundingBox)

			if(!isCollisionWithMapLayer(MainGameScreen.player.boundingBox)){
				MainGameScreen.player.move(game, MainGameScreen.mapMgr.currentMapName)
			}
		}

		//move NPCs
		MainGameScreen.NPCs.moveNPCs()
		camera.position.set(currentPlayerSprite.getX(), currentPlayerSprite.getY(), 0f)
		camera.update

		//update moving NPCs
		MainGameScreen.NPCs.updateMovingNPCs(delta)

		mapRenderer.setView(camera)
		mapRenderer.render()
		mapRenderer.getBatch().begin()
		mapRenderer.getBatch().draw(currentPlayerFrame, currentPlayerSprite.getX, currentPlayerSprite.getY, 1, 1)

		//draw NPCs
		MainGameScreen.NPCs.drawNPCs(mapRenderer.getBatch)

		for(remotePlayer <- MainGameScreen.remotePlayers){
			mapRenderer.getBatch().draw(remotePlayer.currentFrame, remotePlayer.frameSprite.getX, remotePlayer.frameSprite.getY, 1, 1)
		}
		mapRenderer.getBatch().end()
		spriteBatch.begin
		font.draw(spriteBatch, s"FPS:${Gdx.graphics.getFramesPerSecond}", 0, 480)
		spriteBatch.end
	}

	/**
	 * Execute when the screen resize
	 * @param width:Int  Width of the screen
	 * @param height:Int Height of the screen
	 */
	override def resize(width:Int, height:Int){
		MainGameScreen.VIEWPORT.setupViewport(15, 15, width, height)
		camera.setToOrtho(false, MainGameScreen.VIEWPORT.viewportWidth, MainGameScreen.VIEWPORT.viewportHeight)
	}

	/**
	 * Execute when the screen is paused
	 */
	override def pause{}

	/**
	 * Execute when the screen is recover from paused
	 */
	override def resume{}

	/**
	 * Execute when the screen is exited
	 */
	override def dispose{
		MainGameScreen.player.dispose()
		controller.dispose()
		Gdx.input.setInputProcessor(null)
		mapRenderer.dispose()
		font.dispose()
		spriteBatch.dispose()
	}

	/**
	 * Check if the player has collision with the obstacle
	 * @param  boundingBox:Rectangle Bounding box of the player
	 * @return Boolean               True if it has collision with obstacle
	 */
	def isCollisionWithMapLayer(boundingBox:Rectangle):Boolean = {
		val mapCollisionLayer = MainGameScreen.mapMgr.collisionLayer

		if(mapCollisionLayer == null){
			return false
		}

		for(mapObject <- mapCollisionLayer.getObjects().iterator().asScala){
			if(mapObject.isInstanceOf[RectangleMapObject]){
				if(boundingBox.overlaps(mapObject.asInstanceOf[RectangleMapObject].getRectangle())){
					return true
				}
			}
		}
		false
	}

	/**
	 * Update portal layer check if activated
	 * @param  boundingBox:Rectangle Bounding box of the player
	 * @return Boolean               True if activated a portal
	 */
	def updatePortalLayerActivation(boundingBox:Rectangle):Boolean = {
		val mapPortalLayer = MainGameScreen.mapMgr.portalLayer

		if(mapPortalLayer == null){
			return false
		}

		for(mapObject <- mapPortalLayer.getObjects().iterator().asScala){
			if(mapObject.isInstanceOf[RectangleMapObject]){
				if(boundingBox.overlaps(mapObject.asInstanceOf[RectangleMapObject].getRectangle)){
					val mapName = mapObject.asInstanceOf[RectangleMapObject].getName()
					if(mapName == null){
						return false
					}

					//dispose current map's NPCs and generate another map's NPCs
					MainGameScreen.NPCs.disposeNPCs()

					MainGameScreen.mapMgr.setClosestStartPositionFromScaledUnits(MainGameScreen.player.currentPlayerPosition)
					MainGameScreen.mapMgr.loadMap(mapName)
					MainGameScreen.player.init(MainGameScreen.mapMgr.getPlayerStartUnitScaled)

					//set new MapNPCs value to NPCs and init
					MainGameScreen.NPCs = MapNPCs(mapName)

					mapRenderer.setMap(MainGameScreen.mapMgr.currentMap)
					Gdx.app.debug(MainGameScreen.TAG, "Portal Activated")
					return true
				}
			}
		}
		false
	}

}

object MainGameScreen {

	/**
	 * Apply method for creating MainGameScreen
	 * @param  game:Distributedlibgdx2dgame Main game class
	 * @return MainGameScreen               New instance of MainGameScreen
	 */
	def apply(game:Distributedlibgdx2dgame):MainGameScreen = new MainGameScreen(game)

	private val TAG:String = MainGameScreen.getClass.getSimpleName

	var mapMgr:MapManager = MapManager()

	//set first NPCs to TOWN NPCs
	var NPCs = MapNPCs(MapManager.TOWN)
	val player = Player()
	val remotePlayers = new ListBuffer[RemotePlayer]()

	private object VIEWPORT{
		var viewportWidth:Float = 0
		var viewportHeight:Float = 0
		var virtualWidth:Float = 0
		var virtualHeight:Float = 0
		var physicalWidth:Float = 0
		var physicalHeight:Float = 0
		var aspectRatio:Float = 0

		/**
		 * Setup Viewport of the game
		 * @param  width:Int     Expected width of viewport
		 * @param  height:Int    Expected height of viewport
		 * @param  phyWidth:Int  Physical width of the window
		 * @param  phyHeight:Int Physical height of the window
		 */
		def setupViewport(width:Int, height:Int, phyWidth:Int, phyHeight:Int){
			virtualWidth = width
			virtualHeight = height

			viewportWidth = virtualWidth
			viewportHeight = virtualHeight

			physicalWidth = phyWidth
			physicalHeight = phyHeight

			aspectRatio = (virtualWidth / virtualHeight)

			if(physicalWidth / physicalHeight >= aspectRatio){
				viewportWidth = viewportHeight * (physicalWidth / physicalHeight)
				viewportHeight = virtualHeight
			} else {
				viewportWidth = virtualWidth
				viewportHeight = viewportWidth * (physicalHeight / physicalWidth)
			}
			Gdx.app.debug(MainGameScreen.TAG, s"WorldRenderer: virtual: (${virtualWidth},${virtualHeight})" );
			Gdx.app.debug(MainGameScreen.TAG, s"WorldRenderer: viewport: (${viewportWidth},${viewportHeight})" );
			Gdx.app.debug(MainGameScreen.TAG, s"WorldRenderer: physical: (${physicalWidth},${physicalHeight})" );
		}
	}
}