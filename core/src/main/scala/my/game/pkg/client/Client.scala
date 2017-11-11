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

		def move(direction:Direction){
			game.gameUUID.foreach{uuid => clientActor ! Move(uuid, MainGameScreen.mapMgr.currentMapName, direction)}
		}

		def standStill(x:Float, y:Float){
			game.gameUUID.foreach{uuid => clientActor ! StandStill(uuid, MainGameScreen.mapMgr.currentMapName, x, y)}
		}

		def changeMap(mapFrom:String, mapTo:String, x:Float, y:Float){
			game.gameUUID.foreach{uuid => clientActor ! ChangeMap(uuid, mapFrom, mapTo, x, y)}
		}

		def update(delta:Float, map:String, x:Float, y:Float, direction:Direction, state:State){
			timeToPing -= delta
			if(timeToPing <= 0){
				game.gameUUID.foreach{uuid => clientActor ! Alive(uuid, map, x, y, direction, state)}
				timeToPing = 1.4f
			}
		}

		/**
		 * Send status to update server
		 * @param  uuid:String         Client UUID
		 * @param  map:String          Name of the current map
		 * @param  x:Float             X position of player
		 * @param  y:Float             Y position of player
		 * @param  direction:Direction Direction player looking at
		 * @param  frameTime:Float     frameTime of player
		 */
/*		def sendStatus(uuid:String, map:String, x:Float, y:Float, direction:Direction, frameTime:Float){
			clientActor ! Update(uuid, map, x, y, direction, frameTime)
		}*/

		clientActor ! Connect
}