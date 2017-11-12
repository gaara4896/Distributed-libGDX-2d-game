package my.game.pkg.client

import akka.actor.{ActorSystem, Props}

import my.game.pkg.Distributedlibgdx2dgame
import my.game.pkg.screen.MainGameScreen
import my.game.pkg.client.actor.ClientActor
import my.game.pkg.client.dictionary.ClientDictionary._
import my.game.pkg.entity.utils.Direction._
import my.game.pkg.entity.utils.State._

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
		 * Update server when moved
		 * @param  direction:Direction Direction of the player
		 */
		def move(direction:Direction){
			game.gameUUID.foreach{uuid => clientActor ! Move(uuid, MainGameScreen.mapMgr.currentMapName, direction)}
		}

		/**
		 * Update server when standstill
		 * @param  x:Float X position of player
		 * @param  y:Float Y position of player
		 */
		def standStill(x:Float, y:Float){
			game.gameUUID.foreach{uuid => clientActor ! StandStill(uuid, MainGameScreen.mapMgr.currentMapName, x, y)}
		}

		/**
		 * Update server when change map
		 * @param  mapFrom:String Map the player changed from
		 * @param  mapTo:String   Map the player changed to
		 * @param  x:Float        X position of player starting point
		 * @param  y:Float        Y position of player starting point
		 */
		def changeMap(mapFrom:String, mapTo:String, x:Float, y:Float){
			game.gameUUID.foreach{uuid => clientActor ! ChangeMap(uuid, mapFrom, mapTo, x, y)}
		}

		/**
		 * Update server player still alive
		 * @param  delta:Float         Delta time of the frame
		 * @param  map:String          Name of the map
		 * @param  x:Float             X position of the player
		 * @param  y:Float             Y position of the player
		 * @param  direction:Direction Direction of the player
		 * @param  state:State         State of the player
		 * @param  frameTime:Float     Frame time of player
		 */
		def update(delta:Float, map:String, x:Float, y:Float, direction:Direction, state:State, frameTime:Float){
			timeToPing -= delta
			if(timeToPing <= 0){
				game.gameUUID.foreach{uuid => clientActor ! Alive(uuid, map, x, y, direction, state, frameTime)}
				timeToPing = 1.4f
			}
		}

		clientActor ! Connect
}