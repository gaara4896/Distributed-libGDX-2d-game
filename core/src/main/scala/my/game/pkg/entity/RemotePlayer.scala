package my.game.pkg.entity

class RemotePlayer(val playername : String, val gameUUID : String, spritePatch : String) extends PlayerEntity(spritePatch){
}

object RemotePlayer{
	  /**
	    * Apply method for creating RemotePlayer
	    * @return RemotePlayer New instance of RemotePlayer
	    */
	def apply(playername : String, gameUUID : String, spritePatch : String):RemotePlayer = new RemotePlayer(playername, gameUUID, spritePatch)

  	private val TAG:String = RemotePlayer.getClass().getSimpleName()
}
