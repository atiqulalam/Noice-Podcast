package com.noice.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.noice.NoiceApplication
import com.noice.R
import com.noice.model.Comment
import com.noice.model.Episode
import com.noice.model.EventObject
import com.noice.model.User
import com.noice.ui.adapter.CommentAdapter
import com.noice.utils.ImageUtils
import com.noice.utils.Resource
import com.noice.utils.Utils
import com.noice.viewmodel.DetailViewModel
import kotlinx.coroutines.Dispatchers
import org.greenrobot.eventbus.EventBus

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
        var commentAdapter : CommentAdapter ? =null
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

        if (comment.id!=null && !getGetSavedChildComments(comment.id.toString()).isNullOrEmpty()){
            commentAdapter = CommentAdapter(getGetSavedChildComments(comment.id.toString()))
            viewHolder.rvChildComment.adapter = commentAdapter
            viewHolder.rvChildComment.visibility = View.VISIBLE
        }else {
            viewHolder.rvChildComment.visibility = View.GONE
        }

        viewHolder.reply.setOnClickListener {
            viewHolder.edtComment.visibility = View.VISIBLE
            viewHolder.btnSend.visibility = View.VISIBLE
        }

        viewHolder.btnSend.setOnClickListener {
            viewHolder.edtComment.visibility = View.GONE
            viewHolder.btnSend.visibility = View.GONE
            val childComment = Comment()
            if (comment?.id!=null && !viewHolder.edtComment.text.isNullOrEmpty()){
                viewHolder.edtComment.visibility = View.GONE
                viewHolder.btnSend.visibility = View.GONE

                childComment.parentId = comment.id
                childComment.child_count = 0
                childComment.like_count = 0
                childComment.share_count = 0
                childComment.post_date = Utils.getCurrentTimeStamp()
                childComment.episode = Episode()
                childComment.episode?.title = "Fantastic Episode"
                childComment.episode?.image_url = "https://upload.wikimedia.org/wikipedia/en/b/ba/Once_Upon_a_Time_in_Mumbaai.jpg"
                childComment.user = User()
                childComment.user?.name = viewHolder.edtComment.text.toString()
                childComment.user?.image_url = "https://atiqulalam.files.wordpress.com/2015/02/alam1234.jpg?w=640"
                val eventObject = EventObject(childComment,10)
                EventBus.getDefault().post(eventObject)

                commentAdapter?.addAll(childComment)
            }

        }


    }



    fun getGetSavedChildComments(commentId:String):ArrayList<Comment> {
        val str = NoiceApplication.sharedNoicePref.getChildComments(commentId)
        if(str.isNotEmpty()) {
            val listType = object : TypeToken<ArrayList<Comment>>() {}.type
            val list : ArrayList<Comment> = Gson().fromJson(str, listType)

            return list
        }

        return ArrayList()
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
        val reply : TextView = view.findViewById(R.id.txtReply)
        val rvChildComment : RecyclerView = view.findViewById(R.id.rvChildComment)
        val edtComment : EditText = view.findViewById(R.id.edtComment)
        val btnSend : Button = view.findViewById(R.id.btnSend)
    }
}