package com.noice.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.noice.R
import com.noice.helper.GlideApp

object ImageUtils {
    fun setImageByUrlAndCache(context: Context, imageView: ImageView, url: String?, placeHolder: Int = R.drawable.podcast) {
        url?.let {
            GlideApp.with(context)
                .load(it)
                .placeholder(placeHolder)
                .fallback(placeHolder)
                .error(placeHolder)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(imageView)
        }
    }

    fun setImageWithCircleCrop(context: Context, imageView: ImageView, url: String?, placeHolder: Int = R.drawable.podcast) {
        url?.let {
            GlideApp.with(context)
                .load(it)
                .placeholder(placeHolder)
                .fallback(placeHolder)
                .error(placeHolder)
                .dontAnimate()
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(imageView)
        }
    }
}