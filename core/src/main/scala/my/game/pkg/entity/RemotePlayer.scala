package my.game.pkg.entity

import com.badlogic.gdx.graphics.g2d.Sprite
import my.game.pkg.entity.utils.Direction
import my.game.pkg.entity.utils.Direction._

class RemotePlayer(val playername : String, val gameUUID : String, x : Float, y : Float, direction : Direction, frame:Float, spritePatch : String) extends PlayerEntity(spritePatch){

  val frameSprite = new Sprite(currentFrame.getTexture(), x.asInstanceOf[Int], y.asInstanceOf[Int], PlayerEntity.FRAME_WIDTH, PlayerEntity.FRAME_HEIGHT)

  def update(x:Float, y:Float, direction:Direction, frame:Float): Unit ={
    frameSprite.setX(x)
    frameSprite.setY(y)
    frameTime = frame
    currentDirection = direction

    currentDirection match{
      case Direction.LEFT =>
        currentFrame = walkLeftAnimation.getKeyFrame(frameTime)
      case Direction.RIGHT =>
        currentFrame = walkRightAnimation.getKeyFrame(frameTime)
      case Direction.UP =>
        currentFrame = walkUpAnimation.getKeyFrame(frameTime)
      case Direction.DOWN =>
        currentFrame = walkDownAnimation.getKeyFrame(frameTime)
      case _=>
    }
  }

}

object RemotePlayer{
	  /**
	    * Apply method for creating RemotePlayer
	    * @return RemotePlayer New instance of RemotePlayer
	    */
	def apply(playername : String, gameUUID:String, x:Float, y:Float, direction:Direction, frame:Float, spritePatch : String):RemotePlayer = new RemotePlayer(playername, gameUUID,x,y,direction,frame, spritePatch)

  	private val TAG:String = RemotePlayer.getClass().getSimpleName()
}
