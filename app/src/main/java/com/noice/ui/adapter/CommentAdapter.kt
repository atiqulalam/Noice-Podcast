package com.noice.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.noice.model.Comment
import com.noice.ui.view.CommentView
import com.noice.viewmodel.DetailViewModel

class CommentAdapter(val data: ArrayList<Comment>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var content: CommentView? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        content = CommentView(parent.context)
        return content!!.viewHolder
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        content?.viewHolder = holder as CommentView.ViewHolder
        content?.setData(data[position])
    }

    fun addAll(comment:Comment){
        data.add(comment)
        notifyDataSetChanged()
    }
}