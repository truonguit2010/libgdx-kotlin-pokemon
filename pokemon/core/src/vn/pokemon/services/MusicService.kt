package vn.pokemon.services

import com.badlogic.gdx.utils.Disposable
import vn.pokemon.PokemonGame
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Music.OnCompletionListener

/**
 * Created by truongps on 12/10/15.
 */
class MusicService : Disposable {

    val TAG : String = "MusicManager"

    /**
     * Holds the music currently being played, if any.
     */
    var playingMusic : String = ""

    /**
     * The volume to be set on the music.
     */
    var volume : Float = 1f

    /**
     * Whether the music is enabled.
     */
    var enabled : Boolean = true

    var game : PokemonGame

    constructor(game : PokemonGame) {
        this.game = game
    }

//    fun playBackgroundMusic() {
//        if (!enabled) return
//        var music : Music
//    }

    override fun dispose() {

    }
}