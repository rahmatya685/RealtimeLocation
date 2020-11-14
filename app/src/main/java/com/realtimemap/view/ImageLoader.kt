package com.realtimemap.view

import android.widget.ImageView

interface ImageLoader {
    fun loadImage(view: ImageView, url: String)
}