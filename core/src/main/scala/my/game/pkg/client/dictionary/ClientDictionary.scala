package my.game.pkg.client.dictionary

import my.game.pkg.entity.utils.Direction._

object ClientDictionary{
	case object Connect
	case object Quit
	case class Update(uuid:String, map:String, x:Float, y:Float, direction:Direction, frameTime:Float)
}