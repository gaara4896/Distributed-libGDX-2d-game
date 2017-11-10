package my.game.pkg.client.actor

import akka.actor.{Actor, ActorRef}
import akka.util.Timeout

import my.game.pkg.Distributedlibgdx2dgame
import my.game.pkg.screen.MainGameScreen
import my.game.pkg.entity.RemotePlayer
import my.game.pkg.client.dictionary.ClientDictionary._
import my.game.server.dictionary.ServerDictionary._

import scala.util.control.Breaks._

class ClientActor(val ipAddress:String, val port:String, val game:Distributedlibgdx2dgame) extends Actor{	

	val remoteConnection = context.actorSelection(s"akka.tcp://bludbourne@$ipAddress:$port/user/serverconenction")
	val remoteGameServer = context.actorSelection(s"akka.tcp://bludbourne@$ipAddress:$port/user/gameserver")
	def receive = {
		case Connect => remoteConnection ! Connect
		case Connected(uuid) => 
			game.connectServerScreen.updateConnection(true)
			game.gameUUID = Option(uuid)
			context.become(connected)
		case _ => println("Unknown") 
	}

	def connected:Receive = {
		case Quit => remoteConnection ! Quit
		case Update(uuid, map, x, y, direction, frameTime) => remoteGameServer ! Update(uuid, map, x, y, direction, frameTime)
		case UpdatePlayerStatus(uuid, x, y, direction, frameTime) => 
			println("Received")
			var exist:Boolean = false
			breakable{ 
				for( remotePlayer <- MainGameScreen.remotePlayers){
					if(remotePlayer.gameUUID.equals(uuid)){
						remotePlayer.update(x, y, direction, frameTime)
						exist = true
						break
					}
				}
			}
			if(!exist){
				MainGameScreen.remotePlayers += RemotePlayer(uuid, x, y, direction, frameTime)
			}
	}
}

object ClientActor{

	def apply(ipAddress:String, port:String, game:Distributedlibgdx2dgame):ClientActor = new ClientActor(ipAddress, port, game)
}