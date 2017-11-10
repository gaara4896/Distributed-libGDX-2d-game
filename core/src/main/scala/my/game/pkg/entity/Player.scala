package my.game.pkg.entity

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{Animation, Sprite, TextureRegion}
import com.badlogic.gdx.math.{Rectangle, Vector2}
import com.badlogic.gdx.utils.Array

import java.util.UUID

import my.game.pkg.entity.utils.Direction
import my.game.pkg.entity.utils.Direction._
import my.game.pkg.entity.utils.State
import my.game.pkg.entity.utils.State._
import my.game.pkg.assets.AssetsManager
import my.game.pkg.map.MapManager

class Player(val playername : String) extends PlayerEntity {

}

object Player{

	/**
		* Apply method for creating Player
		* @return Player New instance of Player
		*/
	def apply(playername : String):Player = new Player(playername)

	private val TAG:String = Player.getClass().getSimpleName()
	private val defaultSpritePatch:String = "sprites/characters/Warrior.png"
}