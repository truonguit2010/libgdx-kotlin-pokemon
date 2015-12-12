package vn.pokemon.ui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
import com.badlogic.gdx.utils.Scaling
import vn.pokemon.PokemonGame
import java.util.*

/**
 * Created by truongps on 12/12/15.
 */
class LevelClearedDialog : Group {

    var game : PokemonGame
    lateinit var background : Image
    var starImages : ArrayList<Image> = ArrayList()
    var bottomTable : Table
    var yourScore : Label
    var yourTime : Label
    var bestTime : Label
    var centerTable : Table
    var btnMenu : ImageButton
    var btnNext : ImageButton
//    var normalTime : Label

    constructor(game : PokemonGame, numberOfStar : Int) : super() {
        this.game = game

        // Add background
        background = Image(game.assets.levelClearedDialogBackground)
        background.setScaling(Scaling.stretch)
        addActor(background)

        // Add Menu button
        btnMenu = game.uiUtils.createImageButton(null, game.assets.menuButton)
        btnNext = game.uiUtils.createImageButton(null, game.assets.nextButton)

        bottomTable = Table()
        bottomTable.defaults().space(50f)
        bottomTable.add(btnMenu)
        bottomTable.add(btnNext)

        addActor(bottomTable)

        for (i in 0 .. game.assets.stars.size - 1) {
            var drawable = if (i + 1 <= numberOfStar) game.assets.stars[i] else game.assets.emptyStars[i]
            var starImage = Image(drawable)
            starImages.add(starImage)
            addActor(starImage)
        }

        yourScore = game.uiUtils.createLabel("Your score: 100", game.assets.defaultFont, Color.DARK_GRAY)
        yourTime = game.uiUtils.createLabel("Your time: 100", game.assets.defaultFont, Color.DARK_GRAY)
        bestTime = game.uiUtils.createLabel("Best time: 100", game.assets.defaultFont, Color.DARK_GRAY)
        centerTable = Table()
        centerTable.add(bestTime)
        centerTable.row()
        centerTable.add(yourTime)
        centerTable.row()
        centerTable.add(yourScore)
        centerTable.y = 200f
        addActor(centerTable)
    }

    fun addMenuListener(actorGestureListener: ActorGestureListener) {
        this.btnMenu.addListener(actorGestureListener)
    }

    fun addNextListener(actorGestureListener: ActorGestureListener) {
        this.btnNext.addListener(actorGestureListener)
    }

    override fun setWidth(width: Float) {
        super.setWidth(width)
        background.width = width
        bottomTable.width = width
        centerTable.width = width
        starImages[1].x = (width - starImages[1].width) / 2
        starImages[0].x = starImages[1].x - starImages[0].width - 20
        starImages[2].x = starImages[1].x + starImages[1].width + 20
    }

    override fun setHeight(height: Float) {
        super.setHeight(height)
        background.height = height
        starImages[1].y = height / 2
        starImages[0].y = height / 2
        starImages[2].y = height / 2
    }


}