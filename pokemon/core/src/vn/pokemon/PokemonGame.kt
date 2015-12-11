package vn.pokemon

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import vn.pokemon.asset.Assets
import vn.pokemon.screens.GameScreen
import vn.pokemon.screens.MenuScreen
import vn.pokemon.utils.UIUtils

//public class KotlinSample : ApplicationAdapter() {
//	lateinit var batch: SpriteBatch
//	lateinit var img: Texture
//
//	override fun create(): Unit {
//		batch = SpriteBatch()
//		img = Texture("yy_yank.png")
//	}
//
//	override fun render(): Unit {
//		Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
//		batch.begin()
//		batch.draw(img, 0f, 0f)
//		batch.end()
//	}
//}

public class PokemonGame : Game() {

	val TAG = "KotlinSample"
	lateinit var menuScreen : MenuScreen
	lateinit var gameScreen : GameScreen
	lateinit var assets : Assets

	lateinit var uiUtils : UIUtils

	var viewportWidth : Float = 640f
	var viewportHeight : Float = 960f

	override fun create(): Unit {
//		calculateViewport()

		assets = Assets()
		assets.loadSkin()
		assets.loadFont()
		assets.loadBackgrounds()
		assets.loadUis()
		assets.loadPokermons()

		uiUtils = UIUtils()

		gameScreen = GameScreen(this)
		menuScreen = MenuScreen(this)

		setScreen(menuScreen)

	}

	/**
	 * normal
	 *
	 * time
	 *
	 */
	fun showGameScreen(type : String) {
		setScreen(GameScreen(this))
	}

	fun showMenuScreen() {
		setScreen(menuScreen)
	}

	fun calculateViewport() {
		var w = Gdx.graphics.getWidth()
		var h = Gdx.graphics.getHeight()
		if (w == 1920 && h == 1080) {
			Gdx.app.log(TAG, "FullHD")
			viewportWidth = 1136f
			viewportHeight = 640f
		} else if ((w == 1481 && h == 834) || (w == 1635 && h == 921) || (w == 1704 && h == 960)) {
			Gdx.app.log(TAG, "Iphone 6 +")
			viewportWidth = 1136f
			viewportHeight = 640f
		} else if (w == 1280 && h == 768) {
			Gdx.app.log(TAG, "LG F180 ")
			viewportWidth = 1066f
			viewportHeight = 640f
		} else if (w == 1280 && h == 720) {
			Gdx.app.log(TAG, "OPPO find way s U707")
			viewportWidth = 1137f
			viewportHeight = 640f
		} else
			if (w == 1024 && h == 768) {
				Gdx.app.log(TAG, "iPad")
				viewportWidth = 960f
				viewportHeight = 720f
			} else if (w == 2048 && h == 1536) {
				Gdx.app.log(TAG, "iPad retina")
				viewportWidth = 960f
				viewportHeight = 720f
			} else {
				if (w / h > 1.3f && w > 1240 && h > 760) {
					viewportHeight = 720f
					viewportWidth = Math.round(w * viewportHeight / h) as Float
				} else if (w / h > 1.3f && h > 640) {
					viewportHeight = 640f
					viewportWidth = Math.round(w * viewportHeight / h) as Float

				} else {
					viewportWidth = w.toFloat()
					viewportHeight = h.toFloat()

				}
			}
	}

//	override fun render(): Unit {
//		Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
//		batch.begin()
//		batch.draw(img, 0f, 0f)
//		batch.end()
//	}

//	/** Called when the [Application] is first created.  */
//	abstract fun create()
//
//	/** Called when the [Application] is resized. This can happen at any point during a non-paused state but will never happen
//	 * before a call to [.create].
//
//	 * @param width the new width in pixels
//	 * *
//	 * @param height the new height in pixels
//	 */
//	abstract fun resize(width: Int, height: Int)
//
//	/** Called when the [Application] should render itself.  */
//	abstract fun render()
//
//	/** Called when the [Application] is paused, usually when it's not active or visible on screen. An Application is also
//	 * paused before it is destroyed.  */
//	abstract fun pause()
//
//	/** Called when the [Application] is resumed from a paused state, usually when it regains focus.  */
//	abstract fun resume()
//
//	/** Called when the [Application] is destroyed. Preceded by a call to [.pause].  */
//	abstract fun dispose()
}
