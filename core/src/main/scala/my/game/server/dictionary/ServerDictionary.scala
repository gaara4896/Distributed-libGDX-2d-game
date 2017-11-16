package my.game.server.dictionary

import my.game.pkg.entity.utils.Direction._
import my.game.pkg.entity.utils.State._
import my.game.pkg.entity.utils.Job._

import scala.collection.mutable.ListBuffer

object ServerDictionary{
	case object Ping
	case class Connected(uuid:String, job:Job)
	case class PlayerMove(uuid:String, direction:Direction)
	case class PlayerStandStill(uuid:String, job:Job, x:Float, y:Float)
	case class KillPlayer(uuid:String)
	case class NotAlive(uuid:String, map:String)
	case class Correction(uuid:String, job:Job, x:Float, y:Float, direction:Direction, state:State, frameTime:Float)
	case class UpdateNPC(uuid:String, map:String, direction:Direction, x:Float, y:Float, range:Float)
	case class NPCMove(uuid:String, direction:Direction, x:Float, y:Float, range:Float)
	case class NPCInit(npc:ListBuffer[(String, Job, Direction, Float, Float, Float)])
}