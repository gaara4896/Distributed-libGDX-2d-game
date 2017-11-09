package my.game.pkg.client

import akka.actor.{ActorSystem, Props}

import my.game.pkg.client.actor.ClientConnectionActor

class Client(ipAddress:String, port:String){
		val system = ActorSystem("bludBourneClient")
		val clientConnectionActorRef = system.actorOf(Props(new ClientConnectionActor(ipAddress, port)), "clientConnection")

		def connect(){
			clientConnectionActorRef ! "Connect"
		}
}