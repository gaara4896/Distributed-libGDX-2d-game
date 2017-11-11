package my.game.pkg.client.actor

import akka.actor.Actor

import my.game.pkg.Distributedlibgdx2dgame
import my.game.pkg.screen.MainGameScreen
import my.game.pkg.entity.RemotePlayer
import my.game.pkg.client.dictionary.ClientDictionary._
import my.game.server.dictionary.ServerDictionary._

class ClientActor(val ipAddress:String, val port:String, val game:Distributedlibgdx2dgame) extends Actor{	

	val remoteConnection = context.actorSelection(s"akka.tcp://bludbourne@$ipAddress:$port/user/serverconenction")
	val remoteGameServer = context.actorSelection(s"akka.tcp://bludbourne@$ipAddress:$port/user/gameserver")

	/**
	 * Execute when message received
	 */
	def receive = {
		case Connect => remoteConnection ! Connect
		case Connected(uuid) => 
			game.connectServerScreen.updateConnection(true)
			game.gameUUID = Option(uuid)
			context.become(connected)
		case _ => println("Unknown") 
	}

	/**
	 * Execute when message received during connected state
	 * @return Receive Message received
	 */
	def connected:Receive = {
		case Quit(uuid) => remoteConnection ! Quit(uuid)
		case Move(uuid, map, direction) => remoteGameServer ! Move(uuid, map, direction)
		case StandStill(uuid, map, x, y) => remoteGameServer ! StandStill(uuid, map, x, y)
		case ChangeMap(uuid, mapFrom, mapTo, x, y) => remoteGameServer ! ChangeMap(uuid, mapFrom, mapTo, x, y)
		case Alive(uuid, map, x, y, direction, state) => remoteGameServer ! Alive(uuid, map, x, y, direction, state)
/*		case Update(uuid, map, x, y, direction, frameTime) => remoteGameServer ! Update(uuid, map, x, y, direction, frameTime)
		case UpdatePlayerStatus(uuid, x, y, direction, frameTime) => 
			var exist:Boolean = false
			breakable{ 
				for( remotePlayer <- MainGameScreen.remotePlayers){
					if(remotePlayer.uuid.equals(uuid)){
						remotePlayer.update(x, y, direction, frameTime)
						exist = true
						break
					}
				}
			}
			if(!exist){
				MainGameScreen.remotePlayers += RemotePlayer(uuid, x, y, direction, frameTime)
			}*/
	}
}

object ClientActor{

	/**
	 * Apply method for creating ClientActor
	 * @param  ipAddress:String             Ip address of the server
	 * @param  port:String                  Port number of the server
	 * @param  game:Distributedlibgdx2dgame Main game class
	 * @return ClientActor                  New instance of ClientActor
	 */
	def apply(ipAddress:String, port:String, game:Distributedlibgdx2dgame):ClientActor = new ClientActor(ipAddress, port, game)
}