package my.game.pkg.client

import akka.actor.{ActorSystem, Props}

import my.game.pkg.Distributedlibgdx2dgame
import my.game.pkg.client.actor.ClientActor
import my.game.pkg.client.dictionary.ClientDictionary._

class Client(ipAddress:String, port:String, game:Distributedlibgdx2dgame){
		val system = ActorSystem("bludBourneClient")
		val clientActor = system.actorOf(Props(new ClientActor(ipAddress, port, game)), "client")

		clientActor ! Connect
}