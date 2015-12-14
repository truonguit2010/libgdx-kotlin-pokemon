package vn.pokemon.styles

import com.badlogic.gdx.scenes.scene2d.ui.Window
import vn.pokemon.PokemonGame

/**
 * Created by truongps on 12/14/15.
 */
class LeaderBoardDialogStyle : Window.WindowStyle {

    constructor(game : PokemonGame) : super() {
        stageBackground = game.assets.stageBackground
        background = game.assets.leaderBoardDialogBackground
        titleFont = game.assets.defaultFont
    }
}