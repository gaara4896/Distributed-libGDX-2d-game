package my.game.pkg.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.{GL20, OrthographicCamera}
import com.badlogic.gdx.graphics.g2d.{BitmapFont, Sprite, SpriteBatch, TextureRegion}
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Rectangle
import my.game.pkg.Distributedlibgdx2dgame
import my.game.pkg.map.MapManager
import my.game.pkg.controller.PlayerController
import my.game.pkg.entity.{Player, RemotePlayer}
import my.game.pkg.entity.utils.{State, Job}
import my.game.pkg.entity.npc.MapNPCs

import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer

class MainGameScreen(val game:Distributedlibgdx2dgame) extends Screen{

	//var controller : PlayerController
	var currentPlayerFrame:TextureRegion = null
	var currentPlayerSprite : Sprite = null
	var mapRenderer = new OrthogonalTiledMapRenderer(MainGameScreen.mapMgr.getCurrentMap(), MapManager.UNIT_SCALE)
	val camera = new OrthographicCamera()
	val font = new BitmapFont()
	val spriteBatch = new SpriteBatch()

	/**
	 * Execute when no screen is showed
	 */
	override def show{

		MainGameScreen.player = Player(MainGameScreen.job)
		MainGameScreen.controller = PlayerController(MainGameScreen.player, game)
		currentPlayerSprite = MainGameScreen.player.frameSprite

		MainGameScreen.VIEWPORT.setupViewport(15, 15, Gdx.graphics.getWidth(), Gdx.graphics.getHeight())

		camera.setToOrtho(false, MainGameScreen.VIEWPORT.viewportWidth, MainGameScreen.VIEWPORT.viewportHeight)
		mapRenderer.setView(camera)

		Gdx.app.debug(MainGameScreen.TAG, s"UnitScale Value is: ${mapRenderer.getUnitScale()}")

		MainGameScreen.player.init(MainGameScreen.mapMgr.getPlayerStartUnitScaled)
		MainGameScreen.player.move()
		game.client match{
			case Some(_) => MainGameScreen.npc = MapNPCs("Server")
			case None => MainGameScreen.npc.init()
		}
		Gdx.input.setInputProcessor(MainGameScreen.controller)

		MainGameScreen.remotePlayers.clear
		MainGameScreen.pingFromServer = 10f

		game.client.foreach{x => x.join(MainGameScreen.mapMgr.currentMapName)}
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

		MainGameScreen.controller.update(delta)
		MainGameScreen.npc.update(delta)
		currentPlayerFrame = MainGameScreen.player.currentFrame

		if(MainGameScreen.player.state == State.WALKING){
			updatePortalLayerActivation(MainGameScreen.player.boundingBox)

			if(!isCollisionWithMapLayer(MainGameScreen.player.boundingBox)){
				MainGameScreen.player.move()
			}
		}

		for(remotePlayer <- MainGameScreen.remotePlayers){
			remotePlayer.update(delta)
			if(remotePlayer.state == State.WALKING){
				if(!isCollisionWithMapLayer(remotePlayer.boundingBox)){
					remotePlayer.move()
				}
			}
		}

		camera.position.set(currentPlayerSprite.getX(), currentPlayerSprite.getY(), 0f)
		camera.update

		mapRenderer.setView(camera)
		mapRenderer.render()
		mapRenderer.getBatch().begin()
		mapRenderer.getBatch().draw(currentPlayerFrame, currentPlayerSprite.getX, currentPlayerSprite.getY, 1, 1)
		MainGameScreen.npc.draw(mapRenderer.getBatch)
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
		MainGameScreen.controller.dispose()
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

					MainGameScreen.mapMgr.setClosestStartPositionFromScaledUnits(MainGameScreen.player.position)
					MainGameScreen.mapMgr.loadMap(mapName)
					MainGameScreen.player.init(MainGameScreen.mapMgr.getPlayerStartUnitScaled)

					game.client match{
						case Some(_) => MainGameScreen.npc = MapNPCs("Server")
						case None => 
							MainGameScreen.npc = MapNPCs(mapName)
							MainGameScreen.npc.init()
					}

					mapRenderer.setMap(MainGameScreen.mapMgr.currentMap)
					Gdx.app.debug(MainGameScreen.TAG, "Portal Activated")
					game.client match{
						case Some(x) => x.changeMap(MainGameScreen.player.job, MainGameScreen.mapMgr.previousMapName, MainGameScreen.mapMgr.currentMapName,
							MainGameScreen.player.position.x, MainGameScreen.player.position.y)
						case None => 
					}
					MainGameScreen.remotePlayers.clear
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

	var npc = MapNPCs(MapManager.TOWN)
	var job = Job.WARRIOR
	var controller:PlayerController = null
	var player:Player = null
	val remotePlayers = new ListBuffer[RemotePlayer]()
	var pingFromServer = 3f

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