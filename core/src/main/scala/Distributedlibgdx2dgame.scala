package my.game.pkg

import my.game.pkg.screen._
import com.badlogic.gdx.Game

class Distributedlibgdx2dgame extends Game {

	var mainMenuScreen:MainMenuScreen = null
	var mainGameScreen:MainGameScreen = null

	/**
	 * Execute when this class is first instantiate
	 */
	override def create() {
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
