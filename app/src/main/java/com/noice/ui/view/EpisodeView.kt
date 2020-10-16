package com.noice.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.noice.R
import com.noice.model.Episode
import com.noice.utils.ImageUtils

class EpisodeView : FrameLayout {

    private lateinit var ctx: Context
    lateinit var viewHolder: ViewHolder

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context){
        ctx = context
        inflate(context, R.layout.episode_view, this)
        layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        viewHolder = ViewHolder(this)
    }

    fun setData(episode: Episode) {
        if(!episode.image_url.isNullOrEmpty()) {
            ImageUtils.setImageByUrlAndCache(ctx, viewHolder.episodeImage, episode.image_url)
        }
        if(!episode.title.isNullOrEmpty()) {
            viewHolder.episodeTitle.text = episode.title
        }
        if(!episode.duration.isNullOrEmpty()) {
            viewHolder.episodeDuration.text = episode.duration
        }
        if(!episode.date.isNullOrEmpty()) {
            viewHolder.episodeDate.text = episode.date
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val episodeImage: ImageView = view.findViewById(R.id.episodeImage)
        val episodeTitle: TextView = view.findViewById(R.id.episodeTitle)
        val episodeDate: TextView = view.findViewById(R.id.episodeDate)
        val episodeDuration: TextView = view.findViewById(R.id.episodeDuration)
    }
}