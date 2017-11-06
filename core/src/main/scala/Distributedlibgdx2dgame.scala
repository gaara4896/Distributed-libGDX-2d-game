package my.game.pkg

import my.game.pkg.screen.MainGameScreen
import com.badlogic.gdx.Game

class Distributedlibgdx2dgame extends Game {

	/**
	 * Execute when this class is first instantiate
	 */
	override def create() {
		setScreen(Distributedlibgdx2dgame.mainGameScreen)
	}

	/**
	 * Execute when this class is close
	 */
	override def dispose() {
		Distributedlibgdx2dgame.mainGameScreen.dispose
	}
}

object Distributedlibgdx2dgame {
	val mainGameScreen = MainGameScreen()
}
