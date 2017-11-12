package my.game.server.dictionary

import my.game.pkg.entity.utils.Direction._
import my.game.pkg.entity.utils.State._

object ServerDictionary{
	case class Connected(uuid:String, patch:Int)
	case class PlayerMove(uuid:String, direction:Direction)
	case class PlayerStandStill(uuid:String, patch:Int, x:Float, y:Float)
	case class KillPlayer(uuid:String)
	case class NotAlive(uuid:String, map:String)
	case class Correction(uuid:String, patch:Int, x:Float, y:Float, direction:Direction, state:State, frameTime:Float)

	//SpritePatch constants for players
	val warrior = 0
	val rogue = 1
	val mage = 2
	val paladin = 3
	val engineer = 4 
}