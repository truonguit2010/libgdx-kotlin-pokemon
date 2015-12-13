package pokemon.android

import android.os.Bundle

import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import vn.pokemon.PokemonGame

public class AndroidLauncher : AndroidApplication() {
	override protected fun onCreate (savedInstanceState: Bundle?): Unit {
		super.onCreate(savedInstanceState)
		val config = AndroidApplicationConfiguration()
		initialize(PokemonGame(), config)
	}
}
