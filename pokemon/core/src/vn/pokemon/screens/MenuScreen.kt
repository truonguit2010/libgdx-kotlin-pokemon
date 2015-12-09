package vn.pokemon.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
import com.badlogic.gdx.utils.Scaling
import vn.pokemon.PokemonGame

/**
 * Created by truongps on 12/6/15.
 */
class MenuScreen : BaseScreen {

    val TAG : String = "MenuScreen"

    inner class TapListener : ActorGestureListener {
        constructor() : super() {

        }

        override fun tap(event: InputEvent?, x: Float, y: Float, count: Int, button: Int) {
            when (event!!.target.name) {
                "normal_button" -> {
                    game.showGameScreen("normal")
                }
                "time_button" -> {
                    game.showGameScreen("time")
                }
            }
        }

        override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
            event!!.target.color.a = 0.5f
        }

        override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
            event!!.target.color.a = 1f
        }
    }


    constructor(game : PokemonGame) : super(game) {
        // Test for
        for (i  in 3..3) {
            Gdx.app.log(TAG, "test for $i")
        }

        // Init background
        var backgroundImage = Image(game.assets.mainBackground)
        backgroundImage.setSize(width, height)
        backgroundImage.setScaling(Scaling.fill)
        addActor(backgroundImage)
        // Init button play normal mode.

        var normalButton = game.uiUtils.createImageButton(game.assets.greenButtonBackground, game.assets.normalText)
        normalButton.name = "normal_button"
        normalButton.x = width / 2 - normalButton.width / 2
        normalButton.y = height / 2
        normalButton.addListener(TapListener())
        addActor(normalButton)

        var timeButton = game.uiUtils.createImageButton(game.assets.yellowButtonBackground, game.assets.timeText)
        timeButton.name = "time_button"
        timeButton.x = width / 2 - timeButton.width / 2
        timeButton.y = normalButton.y - 10 - timeButton.height
        timeButton.addListener(TapListener())
        addActor(timeButton)
    }

}