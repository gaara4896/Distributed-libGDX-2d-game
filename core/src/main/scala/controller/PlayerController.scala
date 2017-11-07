package my.game.pkg.controller

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.math.Vector3

import java.util.HashMap
import java.util.Map

import my.game.pkg.entity.Player
import my.game.pkg.entity.utils.Direction
import my.game.pkg.entity.utils.State

class PlayerController(val player:Player) extends InputProcessor{

	private val lastMourseCoordinates = new Vector3()

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

	override def keyTyped(character:Char):Boolean = {
		false
	}

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

	override def touchUp(x:Int, y:Int, pointer:Int, button:Int):Boolean = {
		if(button == Input.Buttons.LEFT){
			KeyManager.SELECT = false
		} else if(button == Input.Buttons.RIGHT){
			KeyManager.DOACTION = false
		}
		true
	}

	override def touchDragged(x:Int, y:Int, pointer:Int):Boolean = false

	override def mouseMoved(x:Int, y:Int):Boolean = false

	override def scrolled(amount:Int):Boolean = false

	def update(delta:Float){
		if(KeyManager.LEFT){
			player.update(delta, Direction.LEFT, State.WALKING)
		} else if(KeyManager.RIGHT){
			player.update(delta, Direction.RIGHT, State.WALKING)
		} else if(KeyManager.UP){
			player.update(delta, Direction.UP, State.WALKING)
		} else if(KeyManager.DOWN){
			player.update(delta, Direction.DOWN, State.WALKING)
		} else if(KeyManager.QUIT){
			Gdx.app.exit()
		} else {
			player.update(delta, State.IDLE)
		}
	}

	def dispose(){}

}

object PlayerController{

	def apply(player:Player):PlayerController = new PlayerController(player)

	private val TAG:String = PlayerController.getClass().getSimpleName()

	def hide(){
		KeyManager.LEFT = false
		KeyManager.RIGHT = false
		KeyManager.UP = false
		KeyManager.DOWN = false
		KeyManager.QUIT = false
	}
}