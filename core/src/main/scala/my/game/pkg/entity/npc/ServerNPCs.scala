package my.game.pkg.entity.npc

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2

import my.game.pkg.entity.RemoteNPC
import my.game.pkg.entity.utils.{Direction, Job, State}
import my.game.pkg.entity.utils.Direction._
import my.game.pkg.entity.utils.Job._

import scala.collection.mutable.ListBuffer
import scala.util.control.Breaks._

class ServerNPCs extends MapNPCs {

	val npcList = new ListBuffer[RemoteNPC]

	def init(){}

	/**
	 * Initialize NPCs position
	 */
	def init(serverNpcList:ListBuffer[(String, Job, Direction, Float, Float, Float)]){
		serverNpcList.foreach{serverNpc =>
			npcList += RemoteNPC(serverNpc._1, serverNpc._2, serverNpc._3, serverNpc._4, serverNpc._5, serverNpc._6)
		}
	}

	/**
	 * Update NPCs 
	 * @param delta:Float Delta time of the frame
	 */
	def update(delta:Float){ 
		npcList.foreach{npc => npc.update(delta)}
	}

	/**
	 * Draw NPCs to the screen
	 * @param batch:Batch Batch of the screen
	 */
	def draw(batch:Batch){
		npcList.foreach{npc => batch.draw(npc.currentFrame, npc.frameSprite.getX, npc.frameSprite.getY, 1, 1)}
	}

	def moveNpc(uuid:String, direction:Direction, x:Float, y:Float, range:Float){
		breakable{
			npcList.foreach{npc =>
				if(npc.uuid.equals(uuid)){
					npc.currentDirection = direction
					npc.init(new Vector2(x, y))
					npc.state = State.WALKING
					npc.countDownRange = range
				}
			}
		}
	}

}

object ServerNPCs{

	/**
	 * Apply method for creating CastleOfDoomNPCs
	 * @return CastlrOfDoomNPCs New instance of CastleOfDoomNPCs
	 */
	def apply():ServerNPCs = new ServerNPCs
}
