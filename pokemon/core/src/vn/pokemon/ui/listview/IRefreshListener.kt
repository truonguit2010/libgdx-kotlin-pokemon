package vn.pokemon.ui.listview

import com.badlogic.gdx.scenes.scene2d.Actor

/**
 * Created by truongps on 12/14/15.
 */
interface IRefreshListener {

    fun  onTopRefresh(view : Actor)

    fun onBottomRefresh(view : Actor)
}