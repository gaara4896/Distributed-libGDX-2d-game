package my.game.pkg.client.dictionary

import my.game.pkg.entity.utils.Direction._
import my.game.pkg.entity.utils.State._

object ClientDictionary{
	case object Connect
	case class Quit(uuid:String)
	case class Move(uuid:String, map:String, direction:Direction)
	case class StandStill(uuid:String, map:String, x:Float, y:Float)
	case class ChangeMap(uuid:String, mapFrom:String, mapTo:String, x:Float, y:Float)
	case class Alive(uuid:String, map:String, x:Float, y:Float, direction:Direction, state:State, frameTime:Float)
}