package my.game.pkg

import com.badlogic.gdx.backends.lwjgl._
import my.game.pkg.config.Settings

object Main extends App {
    val config = new LwjglApplicationConfiguration
    config.title = "distributed-libgdx-2d-game"
    config.height = Settings.height
    config.width = Settings.width
    new LwjglApplication(new Distributedlibgdx2dgame, config)
}
