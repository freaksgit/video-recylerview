package com.vstoliarchuk.videorecyclerview.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vstoliarchuk.videorecyclerview.R
import com.vstoliarchuk.videorecyclerview.data.VideoItem
import com.vstoliarchuk.videorecyclerview.data.VideoItemAdapter
import com.vstoliarchuk.videorecyclerview.inflate
import kotlinx.android.synthetic.main.nested_recycler_view_item.view.*

class OuterRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val data by lazy { mutableListOf<List<VideoItem>>() }
    private val commonRecycledViewPool = RecyclerView.RecycledViewPool()

    fun swapData(data: List<List<VideoItem>>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return object : RecyclerView.ViewHolder(parent.inflate(R.layout.nested_recycler_view_item)) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(holder.itemView.nestedRecyclerView) {
//            if (tag == null) {
//                tag = PagerSnapHelper().apply { attachToRecyclerView(this@with) }
//            }
            setRecycledViewPool(commonRecycledViewPool)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            if (adapter == null) {
                adapter = VideoItemAdapter()
            }
            (adapter as VideoItemAdapter).swapData(data[position])
        }
    }

}