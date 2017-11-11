package my.game.pkg

import com.badlogic.gdx.backends.lwjgl._
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Application
import my.game.pkg.config.Settings

object Main extends App {
    val config = new LwjglApplicationConfiguration
    config.title = "distributed-libgdx-2d-game"
    config.height = Settings.height
    config.width = Settings.width
/*    config.foregroundFPS = 30
    config.backgroundFPS = 30*/
    val app:Application = new LwjglApplication(new Distributedlibgdx2dgame, config)

    Gdx.app = app
    Gdx.app.setLogLevel(Application.LOG_DEBUG)
}
