package my.game.pkg.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.Screen
import com.badlogic.gdx.scenes.scene2d.{InputEvent, Stage}
import com.badlogic.gdx.scenes.scene2d.ui.{Label, Table, TextButton, TextField}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener

import my.game.pkg.Distributedlibgdx2dgame
import my.game.pkg.client.Client
import my.game.pkg.assets.AssetsManager

class ConnectServerScreen(val game:Distributedlibgdx2dgame) extends Screen{

	val stage = new Stage()
	val table = new Table()

	val ipLabel = new Label("IP Address:", AssetsManager.STATUSUI_SKIN)
	val ipTextField = new TextField("127.0.0.1", AssetsManager.STATUSUI_SKIN)
	val portLabel = new Label("Port:", AssetsManager.STATUSUI_SKIN)
	val portTextField = new TextField("8080", AssetsManager.STATUSUI_SKIN)
	val connectButton = new TextButton("Connect", AssetsManager.STATUSUI_SKIN)
	val statusLabel = new Label("Not Connected", AssetsManager.STATUSUI_SKIN)
	val playButton = new TextButton("Play Offline", AssetsManager.STATUSUI_SKIN)

	/**
	 * Execute when no screen is showed
	 */
	override def show{
		table.setFillParent(true)

		table.add(ipLabel).spaceBottom(10)
		table.add(ipTextField).width(250).spaceBottom(10).row()
		table.add(portLabel).spaceBottom(10).spaceBottom(25)
		table.add(portTextField).width(250).spaceBottom(25)
		table.add(connectButton).spaceBottom(25).spaceLeft(20).row()
		table.add(statusLabel)
		table.add(playButton).colspan(2).row()

		connectButton.addListener(new ClickListener() {
								
				override def touchDown(event:InputEvent, x:Float, y:Float, pointer:Int, button:Int):Boolean = {
					true
				}

				override def touchUp(event:InputEvent, x:Float, y:Float, pointer:Int, button:Int) {
					if(ipTextField.getText().equals("") || portTextField.getText().equals("")){
						return
					}

					val ipAddress = ipTextField.getText()
					val port = portTextField.getText()

					game.client = Option(new Client(ipAddress, port, game))
				}
			}
		)

		playButton.addListener(new ClickListener() {

				override def touchDown(event:InputEvent, x:Float, y:Float, pointer:Int, button:Int):Boolean = {
					true
				}

				override def touchUp(event:InputEvent, x:Float, y:Float, pointer:Int, button:Int) {
					game.setScreen(game.mainMenuScreen)
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

	def updateConnection(connected:Boolean){
		if(connected){
			statusLabel.setText("Connected")
			playButton.setText("Play Online")
		} else {
			statusLabel.setText("Not Connected")
			playButton.setText("Play Offline")
		}
	}

}

object ConnectServerScreen{

	/**
	 * Apply method for creating ConnectServerScreen
	 * @param  game:Distributedlibgdx2dgame Main game class
	 * @return ConnectServerScreen          New instance of ConnectServerScreen
	 */
	def apply(game:Distributedlibgdx2dgame):ConnectServerScreen = new ConnectServerScreen(game)
}