package com.vstoliarchuk.videorecyclerview.data

import android.net.Uri
import androidx.core.net.toUri
import org.json.JSONObject

data class VideoItem(
    val title: String,
    val subtitle: String,
    val description: String,
    val source: Uri,
    val thumb: String
) {

    companion object {
        fun fromJson(json: JSONObject) = VideoItem(
            json.optString("title"),
            json.optString("subtitle"),
            json.optString("description"),
            json.optString("source").toUri(),
            json.optString("thumb")
        )
    }
}