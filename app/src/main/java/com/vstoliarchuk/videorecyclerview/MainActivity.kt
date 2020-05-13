package com.vstoliarchuk.videorecyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vstoliarchuk.videorecyclerview.recycler.NestedRecyclerViewActivity
import com.vstoliarchuk.videorecyclerview.scroll.NestedScrollViewActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonScrollViewOption.setOnClickListener {
            startActivity(NestedScrollViewActivity.getLaunchingIntent(this@MainActivity))
        }

        buttonRecyclerViewOption.setOnClickListener {
            startActivity(NestedRecyclerViewActivity.getLaunchingIntent(this@MainActivity))
        }
    }
}
