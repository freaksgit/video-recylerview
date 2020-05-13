package com.vstoliarchuk.videorecyclerview.data

import android.content.Context
import org.json.JSONArray

class VideoDataRepository(appContext: Context) {

    val items = loadItemsFromAssets(appContext)

    private fun loadItemsFromAssets(appContext: Context): List<VideoItem> {
        val json = appContext.assets.open("videos.json").reader().readText()
        val videosJson = JSONArray(json)
        val result = mutableListOf<VideoItem>()
        for (i in 0 until videosJson.length()) {
            result.add(VideoItem.fromJson(videosJson.getJSONObject(i)))
        }

        return result
    }


    fun getVideoCategories(limit: Int = 10): List<List<VideoItem>> {
        val result = mutableListOf<List<VideoItem>>()
        repeat(limit) {
            result.add(items.shuffled())
        }
        return result
    }

    fun getVideoItems(): List<VideoItem> {
        return items
    }
}