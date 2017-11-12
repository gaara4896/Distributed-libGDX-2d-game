package my.game.pkg.entity.npc

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2

import my.game.pkg.entity.{NPC, Entity}
import my.game.pkg.entity.utils.Direction

class CastleOfDoomNPCs extends MapNPCs {

	val castleNPC1 = NPC(Entity.spritePatchEngineer, Direction.RIGHT)
	val castleNPC2 = NPC(Entity.spritePatchRogue, Direction.LEFT)
	val castleNPC3 = NPC(Entity.spritePatchPaladin, Direction.RIGHT)
	val castleNPC4 = NPC(Entity.spritePatchEngineer, Direction.LEFT)
	val castleNPC5 = NPC(Entity.spritePatchRogue, Direction.RIGHT)
	val castleNPC6 = NPC(Entity.spritePatchWarrior, Direction.LEFT)
	val castleNPC7 = NPC(Entity.spritePatchRogue, Direction.RIGHT)

	def init(){
		castleNPC1.init(new Vector2(7.2145f, 4.0440f))
		castleNPC2.init(new Vector2(24.0559f, 4.0440f))
		castleNPC3.init(new Vector2(7.2145f, 10.0111f))
		castleNPC4.init(new Vector2(24.0559f, 10.0111f))
		castleNPC5.init(new Vector2(7.2145f, 16.0407f))
		castleNPC6.init(new Vector2(24.0559f, 16.0407f))
		castleNPC7.init(new Vector2(2.5f, 75.5f))

	}

	def update(delta:Float){ }

	def draw(batch: Batch){
		batch.draw(castleNPC1.currentFrame, castleNPC1.frameSprite.getX, castleNPC1.frameSprite.getY, 1, 1)
		batch.draw(castleNPC2.currentFrame, castleNPC2.frameSprite.getX, castleNPC2.frameSprite.getY, 1, 1)
		batch.draw(castleNPC3.currentFrame, castleNPC3.frameSprite.getX, castleNPC3.frameSprite.getY, 1, 1)
		batch.draw(castleNPC4.currentFrame, castleNPC4.frameSprite.getX, castleNPC4.frameSprite.getY, 1, 1)
		batch.draw(castleNPC5.currentFrame, castleNPC5.frameSprite.getX, castleNPC5.frameSprite.getY, 1, 1)
		batch.draw(castleNPC6.currentFrame, castleNPC6.frameSprite.getX, castleNPC6.frameSprite.getY, 1, 1)
		batch.draw(castleNPC7.currentFrame, castleNPC7.frameSprite.getX, castleNPC7.frameSprite.getY, 1, 1)
	}

}

object CastleOfDoomNPCs{
	def apply():CastleOfDoomNPCs = new CastleOfDoomNPCs
}
