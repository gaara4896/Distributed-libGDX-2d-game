package my.game.server.dictionary

import my.game.pkg.entity.utils.Direction._
import my.game.pkg.entity.utils.State._

object ServerDictionary{
	case class Connected(uuid:String)
	case class PlayerMove(uuid:String, direction:Direction)
	case class PlayerStandStill(uuid:String, x:Float, y:Float)
	case class KillPlayer(uuid:String)
	case class NotAlive(uuid:String, map:String)
	case class Correction(uuid:String, x:Float, y:Float, direction:Direction, state:State, frameTime:Float)
}