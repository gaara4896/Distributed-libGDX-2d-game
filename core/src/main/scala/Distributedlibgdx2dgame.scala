package my.game.pkg

import my.game.pkg.screen.MainGameScreen
import com.badlogic.gdx.Game

class Distributedlibgdx2dgame extends Game {

	var mainGameScreen:MainGameScreen = null

	/**
	 * Execute when this class is first instantiate
	 */
	override def create() {
		mainGameScreen = MainGameScreen(this)
		setScreen(mainGameScreen)
	}

	/**
	 * Execute when this class is close
	 */
	override def dispose() {
		mainGameScreen.dispose
	}
}
