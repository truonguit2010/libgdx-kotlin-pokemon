package vn.pokemon.ui

import com.badlogic.gdx.scenes.scene2d.ui.Dialog
import vn.pokemon.PokemonGame
import vn.pokemon.styles.LeaderBoardDialogStyle

/**
 * Created by truongps on 12/14/15.
 */
class LeaderBoardDialog : Dialog {

    constructor(title : String, game : PokemonGame) : super(title, LeaderBoardDialogStyle(game)) {

    }

}