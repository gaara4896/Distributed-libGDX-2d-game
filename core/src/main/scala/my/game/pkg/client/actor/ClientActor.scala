package my.game.pkg.client.actor

import akka.actor.Actor
import my.game.pkg.Distributedlibgdx2dgame
import my.game.pkg.screen.MainGameScreen
import my.game.pkg.entity.{Player, RemotePlayer}
import my.game.pkg.client.dictionary.ClientDictionary._
import my.game.server.dictionary.ServerDictionary._

import scala.util.control.Breaks._

class ClientActor(val ipAddress:String, val port:String, val game:Distributedlibgdx2dgame) extends Actor{	

	val remoteConnection = context.actorSelection(s"akka.tcp://bludbourne@$ipAddress:$port/user/serverconenction")
	val remoteGameServer = context.actorSelection(s"akka.tcp://bludbourne@$ipAddress:$port/user/gameserver")

	/**
	 * Execute when message received
	 */
	def receive = {
		case Connect => remoteConnection ! Connect
		case Connected(uuid, patch) =>
			MainGameScreen.patch = patch
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
		case Quit(uuid) => 
			remoteConnection ! Quit(uuid)
			println("I Quit")
		case Move(uuid, map, direction) => 
			remoteGameServer ! Move(uuid, map, direction)
			println("I move")
		case StandStill(uuid, patch, map, x, y) => 
			remoteGameServer ! StandStill(uuid, patch, map, x, y)
			println("I stand still")
		case ChangeMap(uuid, patch, mapFrom, mapTo, x, y) => remoteGameServer ! ChangeMap(uuid, patch, mapFrom, mapTo, x, y)
			println("I change map")
		case Alive(uuid, patch, map, x, y, direction, state, frameTime) => 
			remoteGameServer ! Alive(uuid, patch, map, x, y, direction, state, frameTime)
			println("I alive")
		
		case PlayerMove(uuid, direction) => 
			if(!uuid.equals(game.gameUUID)){
				breakable{
					for(remotePlayer <- MainGameScreen.remotePlayers){
						if(remotePlayer.uuid.equals(uuid)){
							remotePlayer.setMove(direction)
							break
						}
					}
				}
				println("Move")
			}

		case PlayerStandStill(uuid, patch, x, y) =>
			println(patch)
			if(!uuid.equals(game.gameUUID)){
				var exist:Boolean = false
				breakable{
					for(remotePlayer <- MainGameScreen.remotePlayers){
						if(remotePlayer.uuid.equals(uuid)){
							remotePlayer.setStandStill(x, y)
							exist = true
							break
						}
					}
				}
				if(!exist){
					MainGameScreen.remotePlayers += RemotePlayer(uuid, patch, x, y)
				}
				println("StandStill")
			}
		
		case Correction(uuid, patch, x, y, direction, playerState, frameTime) =>
			println(patch)
			var exist:Boolean = false
			breakable{
				for(remotePlayer <- MainGameScreen.remotePlayers){
					if(remotePlayer.uuid.equals(uuid)){
						remotePlayer.correction(x, y, direction, playerState, frameTime)
						exist = true
						break
					}
				}
			}
			if(!exist){
				MainGameScreen.remotePlayers += RemotePlayer(uuid, patch, x, y)
			}
			println("Correction")
		
		case KillPlayer(uuid) => breakable{
				for(remotePlayer <- MainGameScreen.remotePlayers){
					if(remotePlayer.uuid.equals(uuid)){
						MainGameScreen.remotePlayers -= remotePlayer
					}
				}
			}
			println("KillPlayer")
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