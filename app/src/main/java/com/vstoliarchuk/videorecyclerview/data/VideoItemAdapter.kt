package com.vstoliarchuk.videorecyclerview.data

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.vstoliarchuk.videorecyclerview.R
import com.vstoliarchuk.videorecyclerview.inflate
import kotlinx.android.synthetic.main.video_item.view.*

class VideoItemAdapter : RecyclerView.Adapter<VideoItemAdapter.VideoViewHolder>() {

    private val data: MutableList<VideoItem> by lazy<MutableList<VideoItem>> { mutableListOf() }

    fun swapData(data: List<VideoItem>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder(parent.inflate(R.layout.video_item))
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(data[position])
    }

    class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(videoItem: VideoItem) {
            with(itemView) {
                tvTitle.text = videoItem.title
                tvSubtitle.text = videoItem.subtitle
                ivThumbnail.load("file:///android_asset/${videoItem.thumb}")
            }
        }
    }
}