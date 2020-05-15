package com.vstoliarchuk.videorecyclerview.player

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util


class PlaybackManager(context: Context) {
    private companion object {
        private val TAG = PlaybackManager::class.java.simpleName
    }

    private var currentSource: Uri? = null
    private var exoPlayer: SimpleExoPlayer? = null

    fun play(source: Uri, playerView: PlayerView) {
        val context = playerView.context

        if (currentSource == source) {
            return
        } else {
            currentSource = source
        }
        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(context, Util.getUserAgent(context, "yourApplicationName"))
        val videoSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(source)

        if (exoPlayer == null) {
            exoPlayer = SimpleExoPlayer.Builder(context).build()
        }

        exoPlayer?.prepare(videoSource)
        playerView.player = exoPlayer
        exoPlayer?.playWhenReady = true
    }

    fun stop(source: Uri?) {
        source ?: return
        if (currentSource == source) {
            stop()
        }
    }

    fun stop() {
        currentSource = null
        exoPlayer?.release()
        exoPlayer = null
    }

}