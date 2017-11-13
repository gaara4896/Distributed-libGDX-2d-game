package my.game.pkg.entity

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.{Sprite, TextureRegion}
import com.badlogic.gdx.math.Vector2

import my.game.pkg.entity.utils.Direction._
import my.game.pkg.entity.utils.State
import my.game.pkg.entity.utils.Job._

abstract class Entity(val job:Job){

	var currentDirection:Direction
	var frameTime:Float = 0f
	var state = State.IDLE
	val position = new Vector2()
	var currentFrame:TextureRegion
	val frameSprite:Sprite

}

object Entity{
	private val TAG:String = Entity.getClass().getSimpleName()

	val FRAME_WIDTH = 16
	val FRAME_HEIGHT = 16
}