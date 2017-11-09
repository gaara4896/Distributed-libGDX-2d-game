package my.game.pkg.client.actor

import akka.actor.{Actor, ActorRef}
import akka.util.Timeout

import my.game.pkg.Distributedlibgdx2dgame
import my.game.pkg.client.dictionary.ClientDictionary._
import my.game.server.dictionary.ServerDictionary._

class ClientActor(val ipAddress:String, val port:String, val game:Distributedlibgdx2dgame) extends Actor{	

	val remote = context.actorSelection(s"akka.tcp://bludbourne@$ipAddress:$port/user/serverconenction")

	def receive = {
		case Connect => remote ! Connect
		case Connected => game.connectServerScreen.updateConnection(true)
		case _ => println("Unknown") 
	}
}

object ClientActor{

	def apply(ipAddress:String, port:String, game:Distributedlibgdx2dgame):ClientActor = new ClientActor(ipAddress, port, game)
}