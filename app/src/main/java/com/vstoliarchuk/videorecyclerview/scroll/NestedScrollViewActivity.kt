package com.vstoliarchuk.videorecyclerview.scroll

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

class NestedScrollViewActivity : AppCompatActivity() {

    companion object {
        fun getLaunchingIntent(context: Context) = Intent(context, NestedScrollViewActivity::class.java)
    }

}