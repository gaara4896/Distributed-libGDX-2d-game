package my.game.pkg.entity

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{Animation, Sprite, TextureRegion}
import com.badlogic.gdx.math.{Rectangle, Vector2}
import com.badlogic.gdx.utils.Array
import my.game.pkg.assets.AssetsManager
import my.game.pkg.entity.utils.Direction.Direction
import my.game.pkg.entity.utils.{Direction, State}
import my.game.pkg.entity.utils.State.State
import my.game.pkg.map.MapManager

abstract class PlayerEntity (val spritePatch : String) {

    var currentDirection = Direction.LEFT
    var frameTime:Float = 0f

    AssetsManager.loadTextureAsset(spritePatch)
    val texture:Option[Texture] = AssetsManager.getTextureAsset(spritePatch)
    val walkDownFrames = new Array[TextureRegion](4)
    val walkLeftFrames = new Array[TextureRegion](4)
    val walkRightFrames = new Array[TextureRegion](4)
    val walkUpFrames = new Array[TextureRegion](4)
    texture match{
        case Some(tex) =>
            for(x <- 0 to 3; val textureFrames:scala.Array[scala.Array[TextureRegion]] = TextureRegion.split(tex, PlayerEntity.FRAME_WIDTH, PlayerEntity.FRAME_HEIGHT)){
                for(y <- 0 to 3){
                    x match{
                        case 0 => walkDownFrames.insert(y, textureFrames(x)(y))
                        case 1 => walkLeftFrames.insert(y, textureFrames(x)(y))
                        case 2 => walkRightFrames.insert(y, textureFrames(x)(y))
                        case 3 => walkUpFrames.insert(y, textureFrames(x)(y))
                }
            }
        }
    }
    val walkDownAnimation = new Animation[TextureRegion](0.25f, walkDownFrames, Animation.PlayMode.LOOP)
    val walkLeftAnimation = new Animation[TextureRegion](0.25f, walkLeftFrames, Animation.PlayMode.LOOP)
    val walkRightAnimation = new Animation[TextureRegion](0.25f, walkRightFrames, Animation.PlayMode.LOOP)
    val walkUpAnimation = new Animation[TextureRegion](0.25f, walkUpFrames, Animation.PlayMode.LOOP)

    var currentFrame:TextureRegion = walkDownFrames.get(0)
}

object PlayerEntity{

  private val TAG:String = PlayerEntity.getClass().getSimpleName()

  val spritePatchWarrior = "sprites/characters/Warrior.png"
  val spritePatchRogue = "sprites/characters/Rogue.png"
  val spritePatchEngineer = "sprites/characters/Engineer.png"
  val spritePatchPaladin = "sprites/characters/Paladin.png"
  val spritePatchMage = "sprites/characters/Mage.png"

  val FRAME_WIDTH = 16
  val FRAME_HEIGHT = 16

}
