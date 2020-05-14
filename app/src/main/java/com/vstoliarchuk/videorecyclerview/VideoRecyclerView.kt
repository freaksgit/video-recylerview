package com.vstoliarchuk.videorecyclerview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class VideoRecyclerView : RecyclerView, VisibilityObserver {
    private companion object {
        private val TAG = VideoRecyclerView::class.java.simpleName
    }

    private var currentVisibleView: VisibilityObserver? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        addOnScrollListener(onScrollListener)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        removeOnScrollListener(onScrollListener)
    }

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            Log.d(TAG, "newState = $newState")
            if (newState == SCROLL_STATE_IDLE) {
                handleVisibility()
            }
        }
    }

    private fun handleVisibility(): Boolean {
        val layoutManager = layoutManager
        var handled = false
        if (layoutManager is LinearLayoutManager) {
            val firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()
            val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()
            Log.w(TAG, "firstVisiblePosition = $firstVisiblePosition")


            for (i in firstVisiblePosition..lastVisiblePosition) {
                val viewByPosition = layoutManager.findViewByPosition(i)
                val visibilityPercentage = Utils.calculateFirstVisibleItemPercentage(i, this)

                if (visibilityPercentage < 50) {
                    continue
                }

                if (viewByPosition is VisibilityObserver) {
                    handled = viewByPosition.onVisibilityChanged(true)
                    if (handled) {
                        if (viewByPosition != currentVisibleView) {
                            currentVisibleView?.onVisibilityChanged(false)
                            currentVisibleView = viewByPosition
                        }
                        break
                    }
                }
            }

            if (!handled && currentVisibleView != null) {
                currentVisibleView?.onVisibilityChanged(false)
                currentVisibleView = null
            }
        }
        return handled
    }

    override fun onVisibilityChanged(visible: Boolean): Boolean {
        return if (visible) {
            handleVisibility()
        } else {
            currentVisibleView?.onVisibilityChanged(false)
            currentVisibleView = null
            true
        }
    }
}