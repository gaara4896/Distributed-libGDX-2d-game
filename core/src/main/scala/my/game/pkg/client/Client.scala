package my.game.pkg.client

import akka.actor.{ActorSystem, Props}

import my.game.pkg.Distributedlibgdx2dgame
import my.game.pkg.screen.MainGameScreen
import my.game.pkg.client.actor.ClientActor
import my.game.server.dictionary.ServerDictionary._
import my.game.pkg.entity.utils.Direction._
import my.game.pkg.entity.utils.State._
import my.game.pkg.entity.utils.Job._

class Client(ipAddress:String, port:String, game:Distributedlibgdx2dgame){
		val system = ActorSystem("bludBourneClient")
		val clientActor = system.actorOf(Props(ClientActor(ipAddress, port, game)), "client")
		var timeToPing:Float = 1.4f

		/**
		 * Notify server current player quit
		 */
		def quit(){
			game.gameUUID.foreach{uuid => clientActor ! Quit(uuid)}
		}

		/**
		 * Notify server when player joined game
		 * @param  map:String Map of the player join
		 */
		def join(map:String){
			game.gameUUID.foreach{uuid => clientActor ! Join(uuid, map)}
		}

		/**
		 * Update server when moved
		 * @param  direction:Direction Direction of the player
		 */
		def move(direction:Direction){
			game.gameUUID.foreach{uuid => clientActor ! Move(uuid, MainGameScreen.mapMgr.currentMapName, direction)}
		}

		/**
		 * Update server when standstill
		 * @param  job:Job Job of the player
		 * @param  x:Float X position of player
		 * @param  y:Float Y position of player
		 */
		def standStill(job:Job, x:Float, y:Float){
			game.gameUUID.foreach{uuid => clientActor ! StandStill(uuid, job, MainGameScreen.mapMgr.currentMapName, x, y)}
		}

		/**
		 * Update server when change map
		 * @param  job:Job        Job of the player
		 * @param  mapFrom:String Map the player changed from
		 * @param  mapTo:String   Map the player changed to
		 * @param  x:Float        X position of player starting point
		 * @param  y:Float        Y position of player starting point
		 */
		def changeMap(job:Job, mapFrom:String, mapTo:String, x:Float, y:Float){
			game.gameUUID.foreach{uuid => clientActor ! ChangeMap(uuid, job, mapFrom, mapTo, x, y)}
		}

		/**
		 * Update server player still alive
		 * @param  delta:Float         Delta time of the frame
		 * @param  job:Job             Job of the player
		 * @param  map:String          Name of the map
		 * @param  x:Float             X position of the player
		 * @param  y:Float             Y position of the player
		 * @param  direction:Direction Direction of the player
		 * @param  state:State         State of the player
		 * @param  frameTime:Float     Frame time of player
		 */
		def update(delta:Float, job:Job, map:String, x:Float, y:Float, direction:Direction, state:State, frameTime:Float){
			timeToPing -= delta
			if(timeToPing <= 0){
				game.gameUUID.foreach{uuid => clientActor ! Alive(uuid, job, map, x, y, direction, state, frameTime)}
				timeToPing = 1.4f
			}
			MainGameScreen.pingFromServer -= delta
			if(MainGameScreen.pingFromServer <= 0){
				game.connectServerScreen.updateConnection(false)
				game.setScreen(game.connectServerScreen)
				clientActor ! Disconnected
			}
		}

		clientActor ! Connect
}