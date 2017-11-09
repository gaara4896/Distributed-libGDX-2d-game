package my.game.pkg

import my.game.pkg.screen._
import my.game.pkg.client.Client
import com.badlogic.gdx.Game

class Distributedlibgdx2dgame extends Game {

	var connectServerScreen:ConnectServerScreen = null
	var mainMenuScreen:MainMenuScreen = null
	var mainGameScreen:MainGameScreen = null
	var client:Option[Client] = None

	/**
	 * Execute when this class is first instantiate
	 */
	override def create() {
		connectServerScreen = ConnectServerScreen(this)
		mainGameScreen = MainGameScreen(this)
		mainMenuScreen = MainMenuScreen(this)
		setScreen(mainMenuScreen)
	}

	/**
	 * Execute when this class is close
	 */
	override def dispose() {
		mainGameScreen.dispose
	}
}
