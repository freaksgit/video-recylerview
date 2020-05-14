package com.vstoliarchuk.videorecyclerview.recycler

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnNextLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.vstoliarchuk.videorecyclerview.R
import com.vstoliarchuk.videorecyclerview.data.VideoDataRepository
import kotlinx.android.synthetic.main.activity_nested_recycler_view.*

class NestedRecyclerViewActivity : AppCompatActivity() {

    companion object {
        fun getLaunchingIntent(context: Context) =
            Intent(context, NestedRecyclerViewActivity::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nested_recycler_view)
        val repository = VideoDataRepository(this)
        rootRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = OuterRecyclerViewAdapter().apply { swapData(repository.getVideoCategories()) }
            doOnNextLayout {
                onVisibilityChanged(true)
            }
        }
    }
}