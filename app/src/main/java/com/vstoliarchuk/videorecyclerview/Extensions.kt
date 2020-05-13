package com.vstoliarchuk.videorecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflate(@LayoutRes layoutId: Int, attach: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attach)
}