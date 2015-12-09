package vn.pokemon.utils

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable

/**
 * Created by truongps on 12/8/15.
 */
class UIUtils {

    fun createImageButton(bg : TextureAtlas.AtlasRegion?, icon : TextureAtlas.AtlasRegion) : ImageButton {
        var style = ImageButton.ImageButtonStyle()
        if (bg != null) {
            style.up = TextureRegionDrawable(bg)
        }
        style.imageUp = TextureRegionDrawable(icon)
        var btn = ImageButton(style)
        btn.image.touchable = Touchable.disabled
        return btn
    }
}