package vn.pokemon.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
import com.badlogic.gdx.utils.Scaling
import vn.pokemon.PokemonGame
import vn.pokemon.ui.LevelClearedDialog

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
                    game.showGameScreen("hidden")
                }
                "help_button" -> {
                    // Show help screen
                    game.showHelpScreen()
                }
                "leader_board_button" -> {
                    // Show leader board screen
                }
                "setting_button" -> {
                    // Show Setting button
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

        var helpButton = game.uiUtils.createImageButton(null, game.assets.helpButton)
        helpButton.name = "help_button"
        helpButton.addListener(TapListener())
        var leaderBoardButton = game.uiUtils.createImageButton(null, game.assets.leaderBoardButton)
        leaderBoardButton.name = "leader_board_button"
        leaderBoardButton.addListener(TapListener())
        var settingButton = game.uiUtils.createImageButton(null, game.assets.settingsButton)
        settingButton.name = "setting_button"
        settingButton.addListener(TapListener())

        var table = Table()
        table.add(helpButton)
        var cell = table.add(leaderBoardButton)
        cell.padLeft(50f)
        cell.padRight(50f)
        table.add(settingButton)
        table.width = width
        table.height = game.assets.helpButton.minHeight
        table.y = timeButton.y - 50 - table.height
        addActor(table)

        var levelClearedDialog = LevelClearedDialog(game, 1)
        levelClearedDialog.width = game.assets.levelClearedDialogBackground.minWidth
        levelClearedDialog.height = game.assets.levelClearedDialogBackground.minHeight// * ( ( game.assets.levelClearedDialogBackground.minWidth - levelClearedDialog.width) / levelClearedDialog.width )
        levelClearedDialog.x = (width - levelClearedDialog.width) / 2
        levelClearedDialog.y = (height - levelClearedDialog.height) / 2
        addActor(levelClearedDialog)
    }

}