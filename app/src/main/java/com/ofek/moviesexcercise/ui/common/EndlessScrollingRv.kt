package com.ofek.moviesexcercise.ui.common

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.IllegalStateException

/**
 * a recycler view that allows endless scrolling by creating a callback to when the scroll has ended
 */
class EndlessScrollingRv(context: Context, attrs: AttributeSet?) :
    RecyclerView(context, attrs) {
    var onScrollEndedListener : OnScrollEndedListener? = null
    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
        if (onScrollEndedListener == null || layoutManager == null || adapter == null) {
            return
        }
        val layoutManager = layoutManager as LinearLayoutManager
        if (state == SCROLL_STATE_IDLE) {
            // when the last completely visible item has the same index as the size of the items list it means the rv ended scrolling
            if (layoutManager.findLastCompletelyVisibleItemPosition() == adapter!!.itemCount-1) {
                onScrollEndedListener!!.onScrollEnded()
            }
        }
    }


    interface OnScrollEndedListener {
        // a callback to when the rv is scrolled to it's final position
        fun onScrollEnded()
    }
}