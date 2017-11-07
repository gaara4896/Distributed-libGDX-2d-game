package my.game.pkg.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch

import my.game.pkg.map.MapManager
import my.game.pkg.controller.PlayerController
import my.game.pkg.entity.Player

import scala.collection.JavaConverters._

class MainGameScreen extends Screen{

	private var controller:PlayerController = null
	private var currentPlayerFrame:TextureRegion = null
	private var currentPlayerSprite:Sprite = null
	private var mapRenderer:OrthogonalTiledMapRenderer = null
	private var camera:OrthographicCamera = null
	var font:BitmapFont = null
	var spriteBatch:SpriteBatch = null

	/**
	 * Execute when no screen is showed
	 */
	override def show{
		MainGameScreen.VIEWPORT.setupViewport(15, 15, Gdx.graphics.getWidth(), Gdx.graphics.getHeight())

		font = new BitmapFont
		spriteBatch = new SpriteBatch

		camera = new OrthographicCamera()
		camera.setToOrtho(false, MainGameScreen.VIEWPORT.viewportWidth, MainGameScreen.VIEWPORT.viewportHeight)

		mapRenderer = new OrthogonalTiledMapRenderer(MainGameScreen.mapMgr.getCurrentMap(), MapManager.UNIT_SCALE)
		mapRenderer.setView(camera)

		Gdx.app.debug(MainGameScreen.TAG, s"UnitScale Value is: ${mapRenderer.getUnitScale()}")

		MainGameScreen.player.init(MainGameScreen.mapMgr.getPlayerStartUnitScaled)
		currentPlayerSprite = MainGameScreen.player.frameSprite

		controller = PlayerController(MainGameScreen.player)
		Gdx.input.setInputProcessor(controller)
	}

	/**
	 * Execute when hiding the screen
	 */
	override def hide{}

	/**
	 * Execute each frame to render the screen
	 */
	override def render(delta:Float){
		Gdx.gl.glClearColor(0, 0, 0, 1)
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

		camera.position.set(currentPlayerSprite.getX(), currentPlayerSprite.getY(), 0f)
		camera.update

		MainGameScreen.player.update(delta)
		currentPlayerFrame = MainGameScreen.player.currentFrame

		updatePortalLayerActivation(MainGameScreen.player.boundingBox)

		if(!isCollisionWithMapLayer(MainGameScreen.player.boundingBox)){
			MainGameScreen.player.move()
		}
		controller.update(delta)

		mapRenderer.setView(camera)
		mapRenderer.render()
		mapRenderer.getBatch().begin()
		mapRenderer.getBatch().draw(currentPlayerFrame, currentPlayerSprite.getX, currentPlayerSprite.getY, 1, 1)
		mapRenderer.getBatch().end()
		spriteBatch.begin
		font.draw(spriteBatch, s"FPS:${Gdx.graphics.getFramesPerSecond}", 0, 480)
		spriteBatch.end
	}

	/**
	 * Execute when the screen resize
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
	}

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

					MainGameScreen.mapMgr.setClosestStartPositionFromScaledUnits(MainGameScreen.player.currentPlayerPosition)
					MainGameScreen.mapMgr.loadMap(mapName)
					MainGameScreen.player.init(MainGameScreen.mapMgr.getPlayerStartUnitScaled)
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
	 */
	def apply():MainGameScreen = new MainGameScreen

	private val TAG:String = MainGameScreen.getClass.getSimpleName

	var mapMgr:MapManager = MapManager()
	val player = Player()

	private object VIEWPORT{
		var viewportWidth:Float = 0
		var viewportHeight:Float = 0
		var virtualWidth:Float = 0
		var virtualHeight:Float = 0
		var physicalWidth:Float = 0
		var physicalHeight:Float = 0
		var aspectRatio:Float = 0

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