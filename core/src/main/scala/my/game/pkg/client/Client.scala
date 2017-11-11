package my.game.pkg.client

import akka.actor.{ActorSystem, Props}

import my.game.pkg.Distributedlibgdx2dgame
import my.game.pkg.client.actor.ClientActor
import my.game.pkg.client.dictionary.ClientDictionary._
import my.game.pkg.entity.utils.Direction._

class Client(ipAddress:String, port:String, game:Distributedlibgdx2dgame){
		val system = ActorSystem("bludBourneClient")
		val clientActor = system.actorOf(Props(new ClientActor(ipAddress, port, game)), "client")

	  def quit(){
      clientActor ! Quit
    }

  /**
    *
    * @param uuid
    * @param map
    * @param x
    * @param y
    * @param direction
    * @param frameTime
    */
    def sendStatus(uuid:String, map:String, x:Float, y:Float, direction:Direction, frameTime:Float): Unit ={
      clientActor ! Update(uuid, map, x, y, direction, frameTime)
    }

		clientActor ! Connect
}