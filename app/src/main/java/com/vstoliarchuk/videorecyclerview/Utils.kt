package com.vstoliarchuk.videorecyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

object Utils {
    fun calculateFirstVisibleItemPercentage(firstVisiblePosition: Int, recyclerView: RecyclerView): Int {
        val layoutManager = recyclerView.layoutManager ?: return 0
        val rvRect = Rect()
        recyclerView.getGlobalVisibleRect(rvRect)
        val rowRect = Rect()
        val view = layoutManager.findViewByPosition(firstVisiblePosition) ?: return 0
        view.getGlobalVisibleRect(rowRect)
        var percentFirst: Int

        percentFirst = if (layoutManager is LinearLayoutManager) {
            val orientation = layoutManager.orientation
            if (orientation == LinearLayoutManager.VERTICAL) {
                calculateVerticalVisibility(rowRect, rvRect, view)
            } else {
                calculateHorizontalVisibility(rowRect, rvRect, view)
            }
        } else {
            calculateVerticalVisibility(rowRect, rvRect, view)
        }
        if (percentFirst > 100) percentFirst = 100
        return percentFirst
    }

    private fun calculateHorizontalVisibility(rowRect: Rect, rvRect: Rect, view: View): Int {
        return if (rowRect.right >= rvRect.right) {
            val visibleWidthFirst: Int = rvRect.right - rowRect.left
            visibleWidthFirst * 100 / view.width
        } else {
            val visibleWidthFirst: Int = rowRect.right - rvRect.left
            visibleWidthFirst * 100 / view.width
        }
    }

    private fun calculateVerticalVisibility(rowRect: Rect, rvRect: Rect, view: View): Int {
        return if (rowRect.bottom >= rvRect.bottom) {
            val visibleHeightFirst: Int = rvRect.bottom - rowRect.top
            visibleHeightFirst * 100 / view.height
        } else {
            val visibleHeightFirst: Int = rowRect.bottom - rvRect.top
            visibleHeightFirst * 100 / view.height
        }
    }
}