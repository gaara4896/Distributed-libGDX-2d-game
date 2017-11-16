package my.game.pkg.client.actor

import akka.actor.Actor
import my.game.pkg.Distributedlibgdx2dgame
import my.game.pkg.screen.MainGameScreen
import my.game.pkg.entity.RemotePlayer
import my.game.pkg.entity.npc.ServerNPCs
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
		case Connected(uuid, job) =>
			MainGameScreen.job = job
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
		case Disconnected => 
			context.unbecome()
			remoteConnection ! Quit(game.gameUUID.get)
		case Move(uuid, map, direction) => remoteGameServer ! Move(uuid, map, direction)
		case StandStill(uuid, job, map, x, y) => remoteGameServer ! StandStill(uuid, job, map, x, y)
		case ChangeMap(uuid, job, mapFrom, mapTo, x, y) => remoteGameServer ! ChangeMap(uuid, job, mapFrom, mapTo, x, y)
		case Alive(uuid, job, map, x, y, direction, state, frameTime) => remoteGameServer ! Alive(uuid, job, map, x, y, direction, state, frameTime)
		case Join(uuid, map) => remoteGameServer ! Join(uuid, map)
		case Ping => MainGameScreen.pingFromServer = 5f
		case NPCInit(serverNpcList) => MainGameScreen.npc.asInstanceOf[ServerNPCs].init(serverNpcList)
		case NPCMove(uuid, direction, x, y, range) => MainGameScreen.npc.asInstanceOf[ServerNPCs].moveNpc(uuid, direction, x, y, range)

		case PlayerMove(uuid, direction) => 
			if(!uuid.equals(game.gameUUID.get)){
				breakable{
					for(remotePlayer <- MainGameScreen.remotePlayers){
						if(remotePlayer.uuid.equals(uuid)){
							remotePlayer.setMove(direction)
							break
						}
					}
				}
			}

		case PlayerStandStill(uuid, job, x, y) =>
			if(!uuid.equals(game.gameUUID.get)){
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
					MainGameScreen.remotePlayers += RemotePlayer(uuid, job, x, y)
				}
			}
		
		case Correction(uuid, job, x, y, direction, playerState, frameTime) =>
			if(!uuid.equals(game.gameUUID.get)){
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
					MainGameScreen.remotePlayers += RemotePlayer(uuid, job, x, y)
				}
			}
		
		case KillPlayer(uuid) => breakable{
				for(remotePlayer <- MainGameScreen.remotePlayers){
					if(remotePlayer.uuid.equals(uuid)){
						MainGameScreen.remotePlayers -= remotePlayer
						break
					}
				}
			}
		case _ => println("Connected but unknown")
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