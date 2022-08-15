package com.woowa.banchan.ui.tabs.common

import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

class HorizontalScrollListener : RecyclerView.OnItemTouchListener {
    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        if (rv.canScrollHorizontally(1) || rv.canScrollHorizontally(-1))
            when (e.action) {
                MotionEvent.ACTION_MOVE -> rv.parent
                    .requestDisallowInterceptTouchEvent(true)
                MotionEvent.ACTION_UP -> rv.parent
                    .requestDisallowInterceptTouchEvent(false)
            }
        else
            rv.parent
                .requestDisallowInterceptTouchEvent(false)
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
}