package vn.pokemon.ui.listview

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.Drawable

/**
 * Created by truongps on 12/14/15.
 */
class GroupRefresh : Group {

    class GroupRefreshStyle {

        lateinit var background : Drawable
        /**
         * icon bên trái, tự động xoay khi kéo
         */
        lateinit var arrow : Drawable
        /**
         * font cho câu thông báo
         */
        lateinit var font : BitmapFont
        /**
         * màu font
         */
        var fontColor : Color = Color.GRAY
        /**
         * khoảng cách với viền và giữa icon,label
         */
        var padding : Float = 10f

        /**
         * icon bên trái, tự động xoay ,khi đang ở trạng thái loading
         */
        lateinit  var loading : Drawable
    }

    var arrow : Image
    var label : Label
    var style : GroupRefreshStyle
    var horizontal : Boolean = true
//    var b

    constructor(style : GroupRefresh.GroupRefreshStyle) : super() {
        arrow = Image()
        label = Label("", Label.LabelStyle())
        this.style = style

        if (style.background != null) {

        }
//        this.background = style.background;
//        arrow = new RotateForeverImage(style.arrow);
//        arrow.isRotate = false;
//        label = new Label("", new LabelStyle(style.font, style.fontColor));
//        label.setAlignment(Align.center);

        addActor(arrow);
        addActor(label);
    }

}