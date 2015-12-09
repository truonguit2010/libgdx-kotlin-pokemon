package vn.pokemon.services

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Preferences

/**
 * Created by truongps on 12/10/15.
 */
class PreferenceService {

    val TAG : String = "PreferenceService"
    val PREFS_NAME : String  = "pppp"

    // constants
    val PREF_GAME : String  = "1"

    var pref : Preferences

    constructor() {
        pref = Gdx.app.getPreferences(PREFS_NAME)
    }




}