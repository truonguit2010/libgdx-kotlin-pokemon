package vn.pokemon.asset

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.assets.loaders.resolvers.ResolutionFileResolver
import com.badlogic.gdx.assets.loaders.resolvers.ResolutionFileResolver.Resolution
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import java.util.*

/**
 * Created by truongps on 12/6/15.
 */

class Assets : ManagedAsset {

//    val SKIN : String = "skin/uiskin.json"
//    public lateinit var skin : Skin

    val POKEMON_ATLAS : String = "atlas/pokemons.atlas"
    var pokemons : ArrayList<TextureRegionDrawable> = ArrayList()
    lateinit  var pokemonBackground : TextureRegionDrawable

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
    lateinit var hardText : TextureAtlas.AtlasRegion

    lateinit var backButton : TextureAtlas.AtlasRegion
    lateinit var greenLine : TextureRegionDrawable

    lateinit var helpButton : TextureRegionDrawable
    lateinit var leaderBoardButton : TextureRegionDrawable
    lateinit var settingsButton : TextureRegionDrawable

    lateinit var playButton : TextureRegionDrawable
    lateinit var levelClearedDialogBackground : TextureRegionDrawable
    lateinit var levelInfoDialogBackground : TextureRegionDrawable
    lateinit var nextButton : TextureRegionDrawable
    lateinit var menuButton : TextureRegionDrawable
    var stars : ArrayList<TextureRegionDrawable> = ArrayList()
    var emptyStars : ArrayList<TextureRegionDrawable> = ArrayList()

    val HELP_ATLAS : String = "atlas/helps.atlas"
    var helps : ArrayList<TextureRegionDrawable> = ArrayList()

    val SCORE_FONT = "fonts/font.fnt"
    lateinit var scoreFont : BitmapFont

    val DEFAULT_FONT = "fonts/default.fnt"
    lateinit var defaultFont : BitmapFont

    lateinit var stageBackground : TextureRegionDrawable


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
        loadBitmapFont(DEFAULT_FONT)
        finishLoading()
        scoreFont = getBitmapFont(SCORE_FONT)
        defaultFont = getBitmapFont(DEFAULT_FONT)
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
        hardText = atlas.findRegion("hard_text")

        backButton = atlas.findRegion("back")
        greenLine = TextureRegionDrawable(atlas.findRegion("green_line"))

        helpButton = TextureRegionDrawable(atlas.findRegion("help_button"))
        leaderBoardButton = TextureRegionDrawable(atlas.findRegion("leader_board_button"))
        settingsButton = TextureRegionDrawable(atlas.findRegion("setting_button"))
        playButton = TextureRegionDrawable(atlas.findRegion("play_button"))

        levelClearedDialogBackground = TextureRegionDrawable(atlas.findRegion("level_cleared_dialog"))
        levelInfoDialogBackground = TextureRegionDrawable(atlas.findRegion("level_info"))
        nextButton = TextureRegionDrawable(atlas.findRegion("next_button"))
        menuButton = TextureRegionDrawable(atlas.findRegion("menu_button"))

        for (i in 1 .. 3) {
            stars.add(TextureRegionDrawable(atlas.findRegion("S0" + i)))
            emptyStars.add(TextureRegionDrawable(atlas.findRegion("S0" + i + "_empty")))
        }
    }

    fun loadHelps() {
        loadTextureAtlas(HELP_ATLAS)
        finishLoading()
        var atlas = getTextureAtlas(HELP_ATLAS)
        for (i in 0 .. 3) {
            helps.add(TextureRegionDrawable(atlas.findRegion("help" + (i + 1))))
        }
    }

    fun loadPokermons() {
        loadTextureAtlas(POKEMON_ATLAS)
        finishLoading()
        var atlas = getTextureAtlas(POKEMON_ATLAS)
        for (i in 0 .. 35) {
            pokemons.add(TextureRegionDrawable(atlas.findRegion("" + i + "a")))
        }
        pokemonBackground = TextureRegionDrawable(atlas.findRegion("bg"))
    }

    fun getPokemon(pId : String) : TextureRegionDrawable? {
        for (region in pokemons) {
            if (region != null && pId.equals((region.region as TextureAtlas.AtlasRegion).name)) {
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

    fun genStageBackground() {
//        var p = Pixmap(1, 1, Pixmap.Format.Alpha)
        var p = Pixmap(1, 1, Pixmap.Format.RGBA4444)
        p.setColor(0F, 0F, 0F, 0.3f)
        p.fill()
        var texture = Texture(p)

        stageBackground = TextureRegionDrawable(TextureRegion(texture))
    }
}