package com.woowa.banchan.ui.screen.main.tabs.plan

import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

class NestedScrollListener : RecyclerView.OnItemTouchListener {

    private var lastX: Float = 0f
    private var lastY: Float = 0f

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        if (rv.canScrollHorizontally(1) || rv.canScrollHorizontally(-1))
            when (e.action) {
                MotionEvent.ACTION_DOWN -> {
                    lastX = e.x
                    lastY = e.y
                }
                MotionEvent.ACTION_MOVE -> {
                    val currentX = e.x
                    val currentY = e.y
                    val dx = abs(currentX - lastX)
                    val dy = abs(currentY - lastY)
                    if (dy > dx)
                        rv.parent
                            .requestDisallowInterceptTouchEvent(false)
                    else
                        rv.parent
                        .requestDisallowInterceptTouchEvent(true)
                }
                MotionEvent.ACTION_UP ->
                    rv.parent
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