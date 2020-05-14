package com.vstoliarchuk.videorecyclerview

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import coil.api.load
import com.vstoliarchuk.videorecyclerview.data.VideoItem
import kotlinx.android.synthetic.main.video_item.view.*

class VideoItemView : ConstraintLayout, VisibilityObserver {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var videoItem: VideoItem? = null

    override fun onVisibilityChanged(visible: Boolean): Boolean {
        if (visible) {
            tvTitle.text = "Current Visible VIEW"
            //todo setup video
        } else {
            videoItem?.title?.let {
                tvTitle.text = it
            }
            //todo release video
        }
        return true
    }

    fun bindVideoItem(videoItem: VideoItem) {
        this.videoItem = videoItem
        tvTitle.text = videoItem.title
        tvSubtitle.text = videoItem.subtitle
        ivThumbnail.load("file:///android_asset/${videoItem.thumb}")
    }
}