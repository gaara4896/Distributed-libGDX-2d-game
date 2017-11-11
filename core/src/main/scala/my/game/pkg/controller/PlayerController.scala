package my.game.pkg.controller

import com.badlogic.gdx.{Gdx, Input, InputProcessor}
import com.badlogic.gdx.math.Vector3
import my.game.pkg.Distributedlibgdx2dgame
import my.game.pkg.entity.Player
import my.game.pkg.entity.utils.{Direction, State}

class PlayerController(val player:Player) extends InputProcessor{

	private val lastMourseCoordinates = new Vector3()

	/**
	 * Triggered when any key is pressed
	 * @param  keycode:Int Keycode of the key pressed
	 * @return Boolean     True when is processed
	 */
	override def keyDown(keycode:Int):Boolean = {
		if( keycode == Input.Keys.LEFT || keycode == Input.Keys.A){
			KeyManager.LEFT = true
		} else if( keycode == Input.Keys.RIGHT || keycode == Input.Keys.D){
			KeyManager.RIGHT = true
		} else if( keycode == Input.Keys.UP || keycode == Input.Keys.W){
			KeyManager.UP = true
		} else if( keycode == Input.Keys.DOWN || keycode == Input.Keys.S){
			KeyManager.DOWN = true
		} else if( keycode == Input.Keys.Q || keycode == Input.Keys.ESCAPE){
			KeyManager.QUIT = true
		}
		true
	}

	/**
	 * Triggered when any key is lifted up
	 * @param  keycode:Int Keycode of the key lifted up
	 * @return Boolean     True when is processed
	 */
	override def keyUp(keycode:Int):Boolean = {
		if( keycode == Input.Keys.LEFT || keycode == Input.Keys.A){
			KeyManager.LEFT = false
		} else if( keycode == Input.Keys.RIGHT || keycode == Input.Keys.D){
			KeyManager.RIGHT = false
		} else if( keycode == Input.Keys.UP || keycode == Input.Keys.W){
			KeyManager.UP = false
		} else if( keycode == Input.Keys.DOWN || keycode == Input.Keys.S){
			KeyManager.DOWN = false
		} else if( keycode == Input.Keys.Q || keycode == Input.Keys.ESCAPE){
			KeyManager.QUIT = false
		}
		true
	}

	/**
	 * Triggered when character key is typed
	 * @param  character:Char Character typed
	 * @return Boolean        True when is processed
	 */
	override def keyTyped(character:Char):Boolean = {
		false
	}

	/**
	 * Triggered when mouse button is clicked
	 * @param x:Int       X position of the mouse
	 * @param y:Int       Y position of the mouse
	 * @param pointer:Int Type of pointer
	 * @param button:Int  Button clicked on the mouse
	 * @return Boolean    True when is processed
	 */
	override def touchDown(x:Int, y:Int, pointer:Int, button:Int):Boolean = {
		if( button == Input.Buttons.LEFT || button == Input.Buttons.RIGHT ){
			if(button == Input.Buttons.LEFT){
				KeyManager.SELECT = true
			} else {
				KeyManager.DOACTION = true
			}
			lastMourseCoordinates.set(x, y, 0)
		}
		true
	}

	/**
	 * Triggered when mouse button is lifted
	 * @param x:Int       X position of the mouse
	 * @param y:Int       Y position of the mouse
	 * @param pointer:Int Type of pointer
	 * @param button:Int  Button lifted on the mouse
	 * @return Boolean    True when is processed
	 */
	override def touchUp(x:Int, y:Int, pointer:Int, button:Int):Boolean = {
		if(button == Input.Buttons.LEFT){
			KeyManager.SELECT = false
		} else if(button == Input.Buttons.RIGHT){
			KeyManager.DOACTION = false
		}
		true
	}

	/**
	 * Triggered when mouse is dragged
	 * @param x:Int       X position of the mouse
	 * @param y:Int       Y position of the mouse
	 * @param pointer:Int Type of pointer
	 * @return Boolean    True when is processed
	 */
	override def touchDragged(x:Int, y:Int, pointer:Int):Boolean = false

	/**
	 * Triggered when mouse move
	 * @param x:Int    X position of the mouse
	 * @param y:Int    Y position of the mouse
	 * @return Boolean True when is processed
	 */
	override def mouseMoved(x:Int, y:Int):Boolean = false

	/**
	 * Triggered when mouse is scrolled
	 * @param amount:Int Amount that is scrolled
	 * @return Boolean   True when is processed
	 */
	override def scrolled(amount:Int):Boolean = false

	/**
	 * Update status of player controller
	 * @param  delta:Float Delta value of the time frame
	 */
	def update(delta:Float, game:Distributedlibgdx2dgame){
		if(KeyManager.LEFT){
			player.update(delta, Direction.LEFT, State.WALKING)
		} else if(KeyManager.RIGHT){
			player.update(delta, Direction.RIGHT, State.WALKING)
		} else if(KeyManager.UP){
			player.update(delta, Direction.UP, State.WALKING)
		} else if(KeyManager.DOWN){
			player.update(delta, Direction.DOWN, State.WALKING)
		} else if(KeyManager.QUIT){
			game.client match{
        case Some(x) => x.quit()
      }
			Gdx.app.exit()
		} else {
			player.update(delta, State.IDLE)
		}
	}

	/**
	 * Dispose assets when not needed
	 */
	def dispose(){}

}

object PlayerController{

	/**
	 * Apply method for creating PlayerController
	 * @param  player:Player    Player of the game
	 * @return PlayerController New instance of PlayerController
	 */
	def apply(player:Player):PlayerController = new PlayerController(player)

	private val TAG:String = PlayerController.getClass().getSimpleName()

	/**
	 * Hide everything when the screen is hide
	 */
	def hide(){
		KeyManager.LEFT = false
		KeyManager.RIGHT = false
		KeyManager.UP = false
		KeyManager.DOWN = false
		KeyManager.QUIT = false
	}
}