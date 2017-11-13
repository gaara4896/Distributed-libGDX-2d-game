package my.game.pkg.entity

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.{Sprite, TextureRegion}
import com.badlogic.gdx.math.{Rectangle, Vector2}

import my.game.pkg.entity.utils.Direction
import my.game.pkg.entity.utils.Job._
import my.game.pkg.map.MapManager

abstract class PlayerEntity(job:Job) extends Entity(job){

	val velocity = new Vector2(10f, 10f)
	var currentDirection = Direction.DOWN
	var previousDirection = Direction.UP
	val nextPosition = new Vector2()
	val boundingBox = new Rectangle()
	var currentFrame:TextureRegion = EntitySprite.getFirstTexture(job, currentDirection)
	val frameSprite:Sprite = new Sprite(currentFrame.getTexture(), 0, 0, Entity.FRAME_WIDTH, Entity.FRAME_HEIGHT)

	/**
	 * Set bounding box size for the player
	 * @param widthReduce:Float  Width reduced in percentage
	 * @param heightReduce:Float Height reduced in percentage
	 */
	def setBoundingSize(widthReduce:Float, heightReduce:Float){
		val width = Entity.FRAME_WIDTH * (1.0f - widthReduce)
		val height = Entity.FRAME_HEIGHT * (1.0f - heightReduce)

		if(width == 0 || height == 0){
			Gdx.app.debug(PlayerEntity.TAG, s"Width and Height are 0! $width:$height")
		}

		val minX = nextPosition.x / MapManager.UNIT_SCALE
		val minY = nextPosition.y / MapManager.UNIT_SCALE

		boundingBox.set(minX, minY, width, height)
	}

	/**
	 * Move player
	 */
	def move(){
		position.x = nextPosition.x
		position.y = nextPosition.y
		frameSprite.setX(position.x)
		frameSprite.setY(position.y)
	}
}

object PlayerEntity{

	private val TAG:String = PlayerEntity.getClass().getSimpleName()

}
