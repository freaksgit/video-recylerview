package com.vstoliarchuk.videorecyclerview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class VideoRecyclerView : RecyclerView, VisibilityObserver {
    private companion object {
        private val TAG = VideoRecyclerView::class.java.simpleName
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var currentVisibleView: VisibilityObserver? = null
    var visibilityPercentageThreshold = 65
        set(value) {
            field = when {
                value > 100 -> 100
                value < 0 -> 0
                else -> 0
            }
        }

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

        currentVisibleView?.let {
            val percentage = Utils.calculateVisibilityPercentage(this, it as View)
            if (percentage < visibilityPercentageThreshold) {
                disposeCurrentVisibleView()
            }
        }

        if (layoutManager is LinearLayoutManager) {
            val firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()
            val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()
            Log.w(TAG, "firstVisiblePosition = $firstVisiblePosition")
            for (i in firstVisiblePosition..lastVisiblePosition) {
                val viewByPosition = layoutManager.findViewByPosition(i) ?: continue
                val visibilityPercentage = Utils.calculateVisibilityPercentage(this, viewByPosition)

                if (visibilityPercentage < visibilityPercentageThreshold) {
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
                disposeCurrentVisibleView()
            }
        }
        return handled
    }

    override fun onVisibilityChanged(visible: Boolean): Boolean {
        return if (visible) {
            handleVisibility()
        } else {
            disposeCurrentVisibleView()
            true
        }
    }

    private fun disposeCurrentVisibleView() {
        currentVisibleView?.onVisibilityChanged(false)
        currentVisibleView = null
    }
}