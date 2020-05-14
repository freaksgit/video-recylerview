package com.vstoliarchuk.videorecyclerview

import android.app.Application
import android.content.Context
import com.vstoliarchuk.videorecyclerview.player.PlaybackManager

class App : Application() {

    companion object {

        private var playbackManager: PlaybackManager? = null
        private lateinit var appContext: Context

        fun getPlaybackManager(): PlaybackManager {
            if (playbackManager == null) {
                playbackManager = PlaybackManager(appContext)
            }
            return playbackManager!!
        }
    }


    override fun onCreate() {
        super.onCreate()
        appContext = this
    }
}