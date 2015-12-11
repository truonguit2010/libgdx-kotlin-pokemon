package vn.pokemon.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
import com.badlogic.gdx.utils.Scaling
import vn.pokemon.PokemonGame
import vn.pokemon.ui.PagedScrollPane
import java.util.*

/**
 * Created by truongps on 12/12/15.
 */
class HelpScreen : BaseScreen{

    inner class TapListener : ActorGestureListener {
        constructor() : super() {

        }

        override fun tap(event: InputEvent?, x: Float, y: Float, count: Int, button: Int) {
            when (event!!.target.name) {
                "next_button" -> {
                    game.showMenuScreen()
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

    var scroll : PagedScrollPane

    constructor(game : PokemonGame) : super(game) {

        var background = Image(game.assets.thirthBackground)
        background.setScaling(Scaling.stretch)
        background.setSize(width, height)
        addActor(background)

        scroll = PagedScrollPane()
        scroll.setFlingTime(0.1f)
        scroll.pageSpacing = 1f
        scroll.setSize(width, height)
        var images = ArrayList<Actor>()
        var i = 0
        for (texture in game.assets.helps) {
            var image = Image(texture)
            image.setSize(scroll.width, scroll.height)
            image.x = i * scroll.width
            i++
            images.add(image)
        }
        scroll.addPages(images)
        scroll.touchable = Touchable.enabled
        addActor(scroll)

        var nextButton = game.uiUtils.createImageButton(null, game.assets.playButton)
        nextButton.x = (width - nextButton.width) / 2
        nextButton.y = 10f
        nextButton.name = "next_button"
        nextButton.addListener(TapListener())
        addActor(nextButton)
    }
}