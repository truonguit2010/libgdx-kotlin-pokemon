package vn.pokemon.ui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.Touchable
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
class LevelInfoDialog : Group {

    var dialogGroup : Group
    var game : PokemonGame
    lateinit var background : Image
    lateinit var stageBackground : Image
    var bottomTable : Table
    var infoLabel: Label
    var btnPlayGame: ImageButton
    var btnHelp: ImageButton

    constructor(game : PokemonGame, numberOfStar : Int) : super() {
        touchable = Touchable.enabled
        this.game = game

//        width = game.viewportWidth
//        height = game.viewportHeight
        stageBackground = Image(game.assets.stageBackground)
        stageBackground.touchable = Touchable.enabled
        addActor(stageBackground)

        dialogGroup = Group()
        addActor(dialogGroup)
        // Add background
        background = Image(game.assets.levelInfoDialogBackground)
        background.setScaling(Scaling.stretch)
        dialogGroup.addActor(background)

        // Add Menu button
        btnPlayGame = game.uiUtils.createImageButton(null, game.assets.playButton)
        btnHelp = game.uiUtils.createImageButton(null, game.assets.helpButton)

        bottomTable = Table()
        bottomTable.defaults().space(50f)
        bottomTable.add(btnHelp)
        bottomTable.add(btnPlayGame)


        dialogGroup.addActor(bottomTable)

        infoLabel = game.uiUtils.createLabel("Your score: 100", game.assets.defaultFont, Color.DARK_GRAY)
        infoLabel.height = 600f
        dialogGroup.addActor(infoLabel)
    }

    override fun setWidth(width: Float) {
        super.setWidth(game.viewportWidth)
        stageBackground.width = game.viewportWidth

        dialogGroup.x = (game.viewportWidth - width) / 2
        dialogGroup.width = width

        background.width = width
        bottomTable.width = width
        var padding = 60f
        infoLabel.width = width - padding * 2
        infoLabel.x = padding

    }

    override fun setHeight(height: Float) {
        super.setHeight(game.viewportHeight)
        dialogGroup.height = height
        stageBackground.height = game.viewportHeight
        background.height = height

        dialogGroup.y = (game.viewportHeight - height) / 2
    }


}