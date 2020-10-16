package com.noice.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.noice.R
import com.noice.model.Banner
import com.noice.ui.activity.DetailActivity
import com.noice.utils.ImageUtils
import java.util.*

class BannerAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var layoutInflater: LayoutInflater? = null
    private var ctx: Context = context
    private var data: List<Banner>? = ArrayList()

    init {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    fun addData(data: List<Banner>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = layoutInflater?.inflate(R.layout.banner_view_image_container, null)
        view?.layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        return ViewHolder(view!!)
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        data?.get(position)?.image_url?.let { url ->
            ImageUtils.setImageByUrlAndCache(ctx, (holder as ViewHolder).imageView, url, R.drawable.banner_placeholder)
        }
        holder.itemView.setOnClickListener{
            data?.get(position)?.let { it1 -> DetailActivity.start(ctx, it1) }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView : ImageView = view.findViewById(R.id.image)
    }
}
