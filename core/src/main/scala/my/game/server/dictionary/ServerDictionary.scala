package my.game.server.dictionary

import my.game.pkg.entity.utils.Direction._

object ServerDictionary{
	case class Connected(uuid:String)
	case class UpdatePlayerStatus(uuid:String, x:Float, y:Float, direction:Direction, frameTime:Float)
}