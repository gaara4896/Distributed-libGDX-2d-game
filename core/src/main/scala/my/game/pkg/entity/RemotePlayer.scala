package my.game.pkg.entity

class RemotePlayer(val playername : String, val gameUUID : String) extends PlayerEntity {
}

object RemotePlayer{
  /**
    * Apply method for creating Player
    * @return Player New instance of Player
    */
  def apply(playername : String, gameUUID : String):RemotePlayer = new RemotePlayer(playername, gameUUID)

  private val TAG:String = RemotePlayer.getClass().getSimpleName()
  private val defaultSpritePatch:String = "sprites/characters/Rogue.png"
}
