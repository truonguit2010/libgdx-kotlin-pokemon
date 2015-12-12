package vn.pokemon.utils

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable

/**
 * Created by truongps on 12/8/15.
 */
class UIUtils {

    fun createImageButton(bg : TextureAtlas.AtlasRegion?, icon : TextureAtlas.AtlasRegion) : ImageButton {
        if (bg == null) {
            return createImageButton(null, TextureRegionDrawable(icon))
        } else {
            return createImageButton(TextureRegionDrawable(bg), TextureRegionDrawable(icon))
        }
    }

    fun createImageButton(bg : TextureRegionDrawable?, icon : TextureRegionDrawable) : ImageButton {
        var style = ImageButton.ImageButtonStyle()
        if (bg != null) {
            style.up = bg
        }
        style.imageUp = icon
        var btn = ImageButton(style)
        btn.image.touchable = Touchable.disabled
        return btn
    }

    fun createLabel(text : String, font: BitmapFont, color : Color) : Label {
        var style =  Label.LabelStyle()
        style.font = font
        style.fontColor = color
        var label = Label(text, style)
        return label
    }
}