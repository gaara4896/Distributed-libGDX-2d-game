package my.game.pkg.assets

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.TextureLoader
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader

object AssetsManager{
	private val TAG = AssetsManager.getClass.getSimpleName()
	private val assetManager = new AssetManager()
	private val filePathResolver = new InternalFileHandleResolver()

	/**
	 * Unload asset that are loaded
	 * @param assetFilenamePath:String Path to the asset
	 */
	def unloadAsset(assetFilenamePath:String){
		if(assetManager.isLoaded(assetFilenamePath)){
			assetManager.unload(assetFilenamePath)
		} else {
			Gdx.app.debug(AssetsManager.TAG, s"Asset is not loaded; Nothing to unload: $assetFilenamePath")
		}
	}

	/**
	 * Load map asset
	 * @param mapFilenamePath:String Path to the map asset
	 */
	def loadMapAsset(mapFilenamePath:String){
		if(filePathResolver.resolve(mapFilenamePath).exists()){
			assetManager.setLoader(classOf[TiledMap], new TmxMapLoader(filePathResolver))
			assetManager.load(mapFilenamePath, classOf[TiledMap])
			assetManager.finishLoadingAsset(mapFilenamePath)
			Gdx.app.debug(AssetsManager.TAG, s"Map loaded!: $mapFilenamePath")
		} else {
			Gdx.app.debug(AssetsManager.TAG, s"Map doesn't exist!: $mapFilenamePath")
		}
	}

	/**
	 * Get loaded map asset
	 * @param  mapFilenamePath  Path to the asset
	 * @return Option[TiledMap] Option of Tiled asset if any
	 */
	def getMapAsset(mapFilenamePath:String):Option[TiledMap] = {
		if(assetManager.isLoaded(mapFilenamePath)){
			Option(assetManager.get(mapFilenamePath, classOf[TiledMap]))
		} else {
			Gdx.app.debug(AssetsManager.TAG, s"Map is not loaded: $mapFilenamePath")
			Option[TiledMap](null)
		}
	}

	/**
	 * Load texture asset
	 * @param textureFilenamePath:String Path to the asset
	 */
	def loadTextureAsset(textureFilenamePath:String){
		if(filePathResolver.resolve(textureFilenamePath).exists()){
			assetManager.setLoader(classOf[Texture], new TextureLoader(filePathResolver))
			assetManager.load(textureFilenamePath, classOf[Texture])
			assetManager.finishLoadingAsset(textureFilenamePath)
		} else {
			Gdx.app.debug(AssetsManager.TAG, s"Texture doesn't exist!: $textureFilenamePath")
		}
	}

	/**
	 * Get loaded texture asset
	 * @param  textureFilenamePath:String Path to the asset
	 * @return Option[Texture]            Option of Texture asset if any
	 */
	def getTextureAsset(textureFilenamePath:String):Option[Texture] = {
		if(assetManager.isLoaded(textureFilenamePath)){
			Option(assetManager.get(textureFilenamePath, classOf[Texture]))
		} else {
			Gdx.app.debug(AssetsManager.TAG, s"Texture is not loaded: $textureFilenamePath")
			Option[Texture](null)
		}
	}
}