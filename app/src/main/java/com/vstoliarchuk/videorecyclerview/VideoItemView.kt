package com.vstoliarchuk.videorecyclerview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import coil.Coil
import coil.api.load
import coil.request.LoadRequest
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.vstoliarchuk.videorecyclerview.data.VideoItem
import kotlinx.android.synthetic.main.video_item.view.*

class VideoItemView : ConstraintLayout, VisibilityObserver {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var videoItem: VideoItem? = null
    private var playerView: PlayerView? = null

    override fun onVisibilityChanged(visible: Boolean): Boolean {
        if (visible) {
            insertPlayerViewAndStartPlayback()
        } else {
            removePlayerViewAndStopPlayback()
        }
        return true
    }

    private fun insertPlayerViewAndStartPlayback() {
        val videoItem = videoItem ?: return

        val playerView = if (playerView == null) {
            createPlayerView(videoItem)
        } else {
            playerView
        } ?: return

        if (playerView.parent == null) {
            addView(playerView)
            ivThumbnail.visibility = View.INVISIBLE
        }
        App.getPlaybackManager().play(videoItem.source, playerView)
    }

    private fun createPlayerView(videoItem: VideoItem): PlayerView {
        val newView = PlayerView(context).apply {
            useController = false
            useArtwork = true
            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
            val imageLoader = Coil.imageLoader(context)
            val request = LoadRequest.Builder(context)
                .data("file:///android_asset/${videoItem.thumb}")
                .target { drawable ->
                    defaultArtwork = drawable
                }
                .build()
            imageLoader.execute(request)
        }

        newView.id = View.generateViewId()
        newView.layoutParams = playerViewPlaceholder.layoutParams
        this.removeView(playerViewPlaceholder)
        this.playerView = newView
        return newView
    }

    private fun removePlayerViewAndStopPlayback() {
        ivThumbnail.visibility = View.VISIBLE
        if (playerView?.parent != null) {
            App.getPlaybackManager().stop()
            this.removeView(playerView)
        }
    }

    fun bindVideoItem(videoItem: VideoItem) {
        this.videoItem = videoItem
        tvTitle.text = videoItem.title
        tvSubtitle.text = videoItem.subtitle
        ivThumbnail.load("file:///android_asset/${videoItem.thumb}")
    }
}