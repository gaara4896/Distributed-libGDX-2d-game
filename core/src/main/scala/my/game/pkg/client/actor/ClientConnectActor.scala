package my.game.pkg.client.actor

import akka.actor.{Actor, ActorRef}
import akka.util.Timeout

import my.game.pkg.client.dictionary.ClientDictionary._
import my.game.server.dictionary.ServerDictionary._

class ClientConnectionActor(val ipAddress:String, val port:String) extends Actor{	

	val remote = context.actorSelection(s"akka.tcp://bludbourne@$ipAddress:$port/user/serverconenction")

	def receive = {
		case "Connect" => 
			println("Connecting")
			remote ! Connect
		case Connected => println("Connected to server")
		case _ => println("Unknown") 
	}
}

object ClientConnectionActor{

	def apply(ipAddress:String, port:String):ClientConnectionActor = new ClientConnectionActor(ipAddress, port)
}