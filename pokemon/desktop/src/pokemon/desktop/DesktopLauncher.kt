package pokemon.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import vn.pokemon.PokemonGame

public fun main(arg: Array<String>): Unit {
	val config = LwjglApplicationConfiguration()
	config.width = 320
	config.height = 480
	LwjglApplication(PokemonGame(), config)
}
