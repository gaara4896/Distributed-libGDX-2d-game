package my.game.pkg.entity

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.{Sprite, TextureRegion}
import com.badlogic.gdx.math.Vector2

import my.game.pkg.entity.utils.Direction
import my.game.pkg.entity.utils.State

abstract class Entity{

	var currentDirection = Direction.LEFT
	var frameTime:Float = 0f
	var state = State.IDLE
	val position = new Vector2()
	var currentFrame:TextureRegion
	val frameSprite:Sprite

}

object Entity{
	private val TAG:String = Entity.getClass().getSimpleName()

	val spritePatchWarrior = "sprites/characters/Warrior.png"
	val spritePatchRogue = "sprites/characters/Rogue.png"
	val spritePatchEngineer = "sprites/characters/Engineer.png"
	val spritePatchPaladin = "sprites/characters/Paladin.png"
	val spritePatchMage = "sprites/characters/Mage.png"

	val FRAME_WIDTH = 16
	val FRAME_HEIGHT = 16
}