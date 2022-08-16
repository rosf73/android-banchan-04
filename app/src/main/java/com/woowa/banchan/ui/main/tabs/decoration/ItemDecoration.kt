package com.woowa.banchan.ui.main.tabs.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.ui.extensions.toPx

class ItemDecoration(private val type: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        val count = state.itemCount

        when (type) {
            VERTICAL -> {
                if (position == count - 1) {
                    outRect.bottom = 32f.toPx()
                } else if (position >= 2) {
                    if (position != 2) outRect.top = 4f.toPx()
                    outRect.bottom = 4f.toPx()
                } else {
                    outRect.left = (-8f).toPx()
                    outRect.right = (-8f).toPx()
                }
            }
        }

        when (type) {
            GRID -> {
                val layoutManager = parent.layoutManager as? GridLayoutManager ?: return
                val cols: Int = layoutManager.spanCount

                if (position >= 2) {
                    when (position % cols) {
                        1 -> {
                            outRect.left = 20f.toPx()
                            outRect.bottom = 32f.toPx()
                        }
                        else -> {
                            outRect.right = 20f.toPx()
                            outRect.bottom = 32f.toPx()
                        }
                    }
                } else {
                    outRect.left = (-8f).toPx()
                    outRect.right = (-8f).toPx()
                }
            }
        }
    }

    companion object {
        const val GRID = 0
        const val VERTICAL = 1
    }
}