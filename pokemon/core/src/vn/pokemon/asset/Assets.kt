package vn.pokemon.asset

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.assets.loaders.resolvers.ResolutionFileResolver
import com.badlogic.gdx.assets.loaders.resolvers.ResolutionFileResolver.Resolution
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import java.util.*

/**
 * Created by truongps on 12/6/15.
 */

class Assets : ManagedAsset {

//    val SKIN : String = "skin/uiskin.json"
//    public lateinit var skin : Skin

    val POKEMON_ATLAS : String = "atlas/pokemons.atlas"
    var pokemons : ArrayList<TextureAtlas.AtlasRegion> = ArrayList()

    val BACKGROUND_ATLAS : String = "atlas/background.atlas"
    lateinit var mainBackground : TextureAtlas.AtlasRegion
    lateinit var secondBackground : TextureAtlas.AtlasRegion
    lateinit var thirthBackground : TextureAtlas.AtlasRegion

    val UI_ATLAS : String = "atlas/uis.atlas"
    lateinit var greenButtonBackground : TextureAtlas.AtlasRegion
    lateinit var yellowButtonBackground : TextureAtlas.AtlasRegion
    lateinit var redButtonBackground : TextureAtlas.AtlasRegion

    lateinit var normalText : TextureAtlas.AtlasRegion
    lateinit var timeText : TextureAtlas.AtlasRegion

    lateinit var backButton : TextureAtlas.AtlasRegion

    val SCORE_FONT = "fonts/font.fnt"
    lateinit var scoreFont : BitmapFont


    constructor() : super() {
        resolver = ResolutionFileResolver(InternalFileHandleResolver(), Resolution(640, 960, "640960"))
        manager = AssetManager(resolver)
    }

    fun loadSkin() {
//        loadSkin(SKIN)
//        finishLoading()
//        skin = getSkin(SKIN)
    }

    fun loadFont() {
        loadBitmapFont(SCORE_FONT)
        finishLoading()
        scoreFont = getBitmapFont(SCORE_FONT)
    }

    fun loadBackgrounds() {
        loadTextureAtlas(BACKGROUND_ATLAS)
        finishLoading()
        var atlas = getTextureAtlas(BACKGROUND_ATLAS)
        mainBackground = atlas.findRegion("mainbg")
        secondBackground = atlas.findRegion("secondbg")
        thirthBackground = atlas.findRegion("thbg")
    }

    fun loadUis() {
        loadTextureAtlas(UI_ATLAS)
        finishLoading()
        var atlas = getTextureAtlas(UI_ATLAS)
        greenButtonBackground = atlas.findRegion("green_button_background")
        yellowButtonBackground = atlas.findRegion("yellow_button_background")
        redButtonBackground = atlas.findRegion("red_button_background")

        normalText = atlas.findRegion("normal_text")
        timeText = atlas.findRegion("time_text")

        backButton = atlas.findRegion("back")
    }

    fun loadPokermons() {
        loadTextureAtlas(POKEMON_ATLAS)
        finishLoading()
        var atlas = getTextureAtlas(POKEMON_ATLAS)
        for (i in 0 .. 35) {
            pokemons.add(atlas.findRegion("" + i + "a"))
        }
    }

    fun getPokemon(pId : String) : TextureAtlas.AtlasRegion? {
        for (region in pokemons) {
            if (region != null && pId.equals(region.name)) {
                return region
            }
            if (region == null) {
                Gdx.app.log("Get Pokemon", "region null")
            }
        }
        return null
    }

    override fun load() {

    }

    override fun getLoaded() {

    }

}