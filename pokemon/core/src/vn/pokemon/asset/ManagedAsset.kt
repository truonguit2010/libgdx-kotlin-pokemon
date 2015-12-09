package vn.pokemon.asset

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.resolvers.ResolutionFileResolver
import com.badlogic.gdx.assets.loaders.resolvers.ResolutionFileResolver.Resolution
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import java.util.*

abstract class ManagedAsset {

    lateinit var resolutions : ArrayList<Resolution>

    var manager : AssetManager = AssetManager()

    lateinit  var resolver: ResolutionFileResolver

    var loaded : Boolean = false

    var assigned : Boolean = false

    fun  loadSkin(fileName : String) {
        manager.load(fileName, Skin::class.java)
    }

    // For NinePatch
    fun  loadNinePatch(file : String) {
        manager.load(file, NinePatch::class.java)
    }

    // For texture atlas
    fun loadTextureAtlas(file : String) {
        manager.load(file, TextureAtlas::class.java)
    }

    // For texture atlas
    fun loadTexture(file : String) {
        manager.load(file, Texture::class.java)
    }

    // For bitmap font
    fun loadBitmapFont(file : String) {
        manager.load(file, BitmapFont::class.java)
    }

    // For sound file
    fun loadSound(file : String) {
        manager.load(file, Sound::class.java)
    }

    // for music file
    fun loadMusic(file : String) {
        manager.load(file, Music::class.java)
    }

    // for particle Effect file
    fun loadParticleEffect(file : String) : ParticleEffect {
        var  effect = ParticleEffect()
        var imgPath = resolver.resolve(file);
        var imgDir = imgPath.parent();
        effect.load(imgPath, imgDir);
        return effect;
    }

    fun  getSkin(file : String) : Skin {
        return manager.get(file, Skin::class.java)
    }

    /**
     *
     * @param file
     * @return
     */
    fun  getNinePatch(file : String) : NinePatch{
        return manager.get(file, NinePatch::class.java)
    }

    /**
     *
     * @param file
     * @return
     */
    fun  getTextureAtlas(file : String) : TextureAtlas {
        return manager.get(file, TextureAtlas::class.java)
    }

    fun  getTexture(file : String) : Texture{
        return manager.get(file, Texture::class.java)
    }

    // getting the bitmap font file

    fun  getBitmapFont(file : String) : BitmapFont {
        return manager.get(file, BitmapFont::class.java)
    }

    // getting the sound file

    fun getSound(file : String) : Sound {
        return manager.get(file, Sound::class.java)
    }

    // getting the music file

    fun  getMusic(file : String) : Music {
        return manager.get(file, Music::class.java)
    }


    abstract fun load()

    abstract fun getLoaded()

    fun update() : Boolean {
        manager.clear()
        return manager.update()
    }

    fun clear() {
        loaded = false
        assigned = false
        manager.clear()
    }

    fun finishLoading() {
        manager.finishLoading()
    }

    fun dispose() {
        manager.dispose()
    }

}