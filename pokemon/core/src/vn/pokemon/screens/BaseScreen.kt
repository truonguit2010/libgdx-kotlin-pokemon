package vn.pokemon.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.utils.Scaling
import com.badlogic.gdx.utils.viewport.ScalingViewport
import vn.pokemon.PokemonGame

/**
 * Created by truongps on 12/6/15.
 */
open class BaseScreen : Stage, Screen {

    var game : PokemonGame

    constructor(game : PokemonGame) : super(ScalingViewport(Scaling.stretch, game.viewportWidth, game.viewportHeight, OrthographicCamera())) {
        this.game = game
    }

//    Scaling.stretch, VIEWPORT_WIDTH,
//    VIEWPORT_HEIGHT, new OrthographicCamera())
    /** Called when this screen becomes the current screen for a [Game].  */
    override public  fun show() {
        Gdx.input.setInputProcessor(this)
    }

    /** Called when the screen should render itself.
     * @param delta The time in seconds since the last render.
     */
    override  public  fun render(delta: Float) {
        // clear the screen with the given RGB color (black)
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        act(delta)
        // draw the actors
        draw()
    }

    /** @see ApplicationListener.resize
     */
    override  public  fun resize(width: Int, height: Int) {

    }

    /** @see ApplicationListener.pause
     */
    override public  fun pause() {}

    /** @see ApplicationListener.resume
     */
    override public   fun resume() {}

    /** Called when this screen is no longer the current screen for a [Game].  */
    override public   fun hide() {}

    /** Called when this screen should release all resources.  */
    override public   fun dispose() {}
}