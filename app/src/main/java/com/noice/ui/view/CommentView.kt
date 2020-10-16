package com.noice.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.noice.R
import com.noice.model.Comment
import com.noice.utils.ImageUtils

/**
 * Created By Atiq
 */
class CommentView : FrameLayout {

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
        inflate(context, R.layout.comment_view, this)
        layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        viewHolder = ViewHolder(this)
    }

    fun setData(comment: Comment) {
        if(!comment.user?.name.isNullOrEmpty()) {
            viewHolder.userName.text = comment.user?.name
        }
        if(!comment.user?.image_url.isNullOrEmpty()) {
            ImageUtils.setImageWithCircleCrop(ctx, viewHolder.userImage, comment.user?.image_url, R.drawable.ic_user_placeholder)
        }
        if(!comment.post_date.isNullOrEmpty()) {
            viewHolder.txtPostDate.text = comment.post_date
        }
        if(!comment.comment.isNullOrEmpty()) {
            viewHolder.txtComment.text = comment.comment
        }
        if(!comment.episode?.image_url.isNullOrEmpty()) {
            ImageUtils.setImageByUrlAndCache(ctx, viewHolder.episodeImage, comment.episode?.image_url)
        }
        if(!comment.episode?.title.isNullOrEmpty()) {
            viewHolder.txtTitle.text = comment.episode?.title
        }
        if(comment.child_count != null && comment.child_count != 0) {
            viewHolder.commentCount.text = comment.child_count.toString()
        }
        if(comment.like_count != null && comment.like_count != 0) {
            viewHolder.likeBtn.text = comment.like_count.toString()
        }
        if(comment.share_count != null && comment.share_count != 0) {
            viewHolder.shareCount.text = comment.share_count.toString()
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userImage : ImageView = view.findViewById(R.id.userImage)
        val userName : TextView = view.findViewById(R.id.txtName)
        val txtPostDate : TextView = view.findViewById(R.id.txtDate)
        val txtComment : TextView = view.findViewById(R.id.comment)
        val episodeImage : ImageView = view.findViewById(R.id.episodeImage)
        val txtTitle : TextView = view.findViewById(R.id.txtTitle)
        val commentCount : TextView = view.findViewById(R.id.txtCommentCount)
        val likeBtn : TextView = view.findViewById(R.id.txtLikeBtn)
        val shareCount : TextView = view.findViewById(R.id.txtShareCount)
    }
}