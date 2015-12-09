package vn.pokemon.ui

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import vn.pokemon.model.*

/**
 * Created by truongps on 12/7/15.
 */
class PokemonImage : Image {

    var pId : String
    var point : Point

    constructor(pId : String, point : Point, region : TextureAtlas.AtlasRegion) : super(region) {
        this.point = point
        this.pId = pId
    }


}