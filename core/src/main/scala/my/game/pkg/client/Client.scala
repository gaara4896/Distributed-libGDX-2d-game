package my.game.pkg.client

import akka.actor.{ActorSystem, Props}

import my.game.pkg.Distributedlibgdx2dgame
import my.game.pkg.client.actor.ClientActor
import my.game.pkg.client.dictionary.ClientDictionary._
import my.game.pkg.entity.utils.Direction._

class Client(ipAddress:String, port:String, game:Distributedlibgdx2dgame){
		val system = ActorSystem("bludBourneClient")
		val clientActor = system.actorOf(Props(ClientActor(ipAddress, port, game)), "client")

		/**
		 * Notify server current player quit
		 */
		def quit(){
			clientActor ! Quit
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
		def sendStatus(uuid:String, map:String, x:Float, y:Float, direction:Direction, frameTime:Float){
			clientActor ! Update(uuid, map, x, y, direction, frameTime)
		}

		clientActor ! Connect
}