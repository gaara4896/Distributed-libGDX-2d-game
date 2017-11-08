package my.game.pkg.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.Screen
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener

import my.game.pkg.Distributedlibgdx2dgame
import my.game.pkg.assets.AssetsManager

class MainMenuScreen(val game:Distributedlibgdx2dgame) extends Screen{

	val stage = new Stage()
	val table = new Table()

	/**
	 * Execute when no screen is showed
	 */
	override def show{
		table.setFillParent(true)

		val title = new Image(AssetsManager.STATUSUI_TEXTUREATLAS.findRegion("bludbourne_title"))
		val newGameButton = new TextButton("New Game", AssetsManager.STATUSUI_SKIN)
		val loadGameButton = new TextButton("Load Game", AssetsManager.STATUSUI_SKIN)
		val instructionButton = new TextButton("Instruction", AssetsManager.STATUSUI_SKIN)
		val exitButton = new TextButton("Exit", AssetsManager.STATUSUI_SKIN)

		table.add(title).spaceBottom(75).row()
		table.add(newGameButton).spaceBottom(10).row()
		table.add(loadGameButton).spaceBottom(10).row()
		table.add(instructionButton).spaceBottom(10).row()
		table.add(exitButton).spaceBottom(10).row()

		newGameButton.addListener(new ClickListener() {
								
									  override def touchDown(event:InputEvent, x:Float, y:Float, pointer:Int, button:Int):Boolean = {
										  true
									  }

									  override def touchUp(event:InputEvent, x:Float, y:Float, pointer:Int, button:Int) {
										  game.setScreen(game.mainGameScreen)
									  }
								  }
		)

		loadGameButton.addListener(new ClickListener() {

									   override def touchDown(event:InputEvent, x:Float, y:Float, pointer:Int, button:Int):Boolean = {
										   true
									   }

									   override def touchUp(event:InputEvent, x:Float, y:Float, pointer:Int, button:Int) {
										   game.setScreen(game.mainGameScreen)
									   }
								   }
		)

		instructionButton.addListener(new ClickListener() {

										  override def touchDown(event:InputEvent, x:Float, y:Float, pointer:Int, button:Int):Boolean = {
										  	  true
										  }

										  override def touchUp(event:InputEvent, x:Float, y:Float, pointer:Int, button:Int) {
										  }
		})

		exitButton.addListener(new ClickListener() {

								   override def touchDown(event:InputEvent, x:Float, y:Float, pointer:Int, button:Int):Boolean = {
									   true
								   }

								   override def touchUp(event:InputEvent, x:Float, y:Float, pointer:Int, button:Int) {
									   Gdx.app.exit()
								   }

							   }
		)

		stage.addActor(table)
		Gdx.input.setInputProcessor(stage)
	}

	/**
	 * Execute when hiding the screen
	 */
	override def hide{}

	/**
	 * Execute each frame to render the screen
	 * @param delta:Float Time delta of the frame
	 */
	override def render(delta:Float){
		Gdx.gl.glClearColor(0, 0, 0, 1)
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

		stage.act(delta)
		stage.draw()
	}

	/**
	 * Execute when the screen resize
	 * @param width:Int  Width of the screen
	 * @param height:Int Height of the screen
	 */
	override def resize(width:Int, height:Int){
		stage.getViewport().setScreenSize(width, height)
	}

	/**
	 * Execute when the screen is paused
	 */
	override def pause{}

	/**
	 * Execute when the screen is recover from paused
	 */
	override def resume{}

	/**
	 * Execute when the screen is exited
	 */
	override def dispose{
		stage.dispose()
	}

}

object MainMenuScreen{

	/**
	 * Apply method for creating MainMenuScreen
	 * @param  game:Distributedlibgdx2dgame Main game class
	 * @return MainMenuScreen               New instance of MainMenuScreen
	 */
	def apply(game:Distributedlibgdx2dgame):MainMenuScreen = new MainMenuScreen(game)
}