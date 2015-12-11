package vn.pokemon.ui

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Cell
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Array;
import java.util.*

/**
 * Created by truongps on 12/12/15.
 */
class PagedScrollPane : ScrollPane {

    var pickAmountX : Float = 0f
    var pickDifferenceX : Float = -1f

    var wasPanDragFling : Boolean = false

    var scrollToPageSpeed : Float = 1000f

    var pageSpacing : Float = 0f
    set(value) {
        field = value
        if (content != null) {
            content.defaults().space(value)
            for (cell in content.cells) {
                cell.space(value)
            }
            content.invalidate()
        }
    }

    var content : Table

    constructor() : super(null) {
        content = Table()
        content.defaults().space(50f)
        setWidget(content)
    }

    constructor(skin : Skin) : super(null, skin) {
        content = Table()
        content.defaults().space(50f)
        setWidget(content)
    }

    constructor(skin : Skin, styleName : String) : super(null, skin, styleName) {
        content = Table()
        content.defaults().space(50f)
        setWidget(content)
    }

    constructor (widget : Actor, style : ScrollPaneStyle) : super(null, style) {
        content = Table()
        content.defaults().space(50f)
        setWidget(content)
    }

    fun  addPages (pages : ArrayList<Actor>) {
        for (page in pages) {
            content.add(page).expandY().fillY()
        }
    }

    fun addPage (page : Actor) {
        content.add(page).expandY().fillY()
    }

    override fun act(delta: Float) {
        super.act(delta)
        if (wasPanDragFling && !isPanning() && !isDragging() && !isFlinging()) {
            wasPanDragFling = false
            scrollToPage()
        } else {
            if (isPanning() || isDragging() || isFlinging()) {
                wasPanDragFling = true
            }
        }
    }

    override fun setWidth(width: Float) {
        super.setWidth(width)
        if (content != null) {
            for (cell in content.cells) {
                cell.width(width)
            }
            content.invalidate()
        }
    }



//    fun setPageSpacing(a : Float) {
//        if (content != null) {
//            content.defaults().space(a)
//            for (cell in content.cells) {
//                cell.space(a)
//            }
//            content.invalidate()
//        }
//    }

    //	private int currentPage;
    //	public boolean scrollToNextPage () {
    //		final float width = getWidth();
    //		final float scrollX = getScrollX();
    //		final float maxX = getMaxX();
    //
    //		if (scrollX >= maxX || scrollX <= 0) return false;
    //
    //		Array<Actor> pages = content.getChildren();
    //		float pageX = 0;
    //		float pageWidth = 0;
    //		if (pages.size > 0) {
    //			for (Actor a : pages) {
    //				pageX = a.getX();
    //				pageWidth = a.getWidth();
    //				if (scrollX < (pageX + pageWidth * 0.5)) {
    //					break;
    //				}
    //			}
    //			setScrollX(MathUtils.clamp(pageX - (width - pageWidth) / 2, 0, maxX));
    //			return true;
    //		}
    //		return false;
    //	}

    fun scrollToPage () {
        var width = getWidth()
        var scrollX = getScrollX()
        var maxX = getMaxX()

        if (scrollX >= maxX || scrollX <= 0) return

        var pages = content.getChildren()
        var pageX = 0f
        var pageWidth = 0f
        if (pages.size > 0) {
            for (a in pages) {
                pageX = a.x
                pageWidth = a.width
                if (scrollX < (pageX + pageWidth * 0.5f)) {
                    break;
                }
            }
            setScrollX(MathUtils.clamp(pageX - (width - pageWidth) / 2, 0f, maxX))
        }
    }
}