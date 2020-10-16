package com.noice.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.noice.R
import com.noice.model.Banner
import com.noice.model.Comment
import com.noice.model.Episode
import com.noice.model.User
import com.noice.ui.adapter.CommentAdapter
import com.noice.viewmodel.PodCastDetailViewModel
import com.noice.utils.ResponseStatus
import com.noice.utils.Utils
import com.noice.ui.view.ErrorView
import kotlinx.android.synthetic.main.fragment_comments.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class CommentsFragment : Fragment() {

    companion object {
        private const val POD_CAST_DATA = "POD_CAST_DATA"

        fun newInstance(podCast: Banner) : CommentsFragment {
            val fragment = CommentsFragment()
            val bundle = Bundle()
            bundle.putParcelable(POD_CAST_DATA, podCast)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var podCast: Banner? = null
    private var commentAdapter: CommentAdapter? = null
    private lateinit var viewModel: PodCastDetailViewModel
    private var comments = ArrayList<Comment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        podCast = arguments?.getParcelable(POD_CAST_DATA)
        EventBus.getDefault().register(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(PodCastDetailViewModel::class.java)
        return inflater.inflate(R.layout.fragment_comments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        errorView.showLoading()

        initViews()

        if(podCast?.id != null) {
            getComments()
        }
    }

    private fun initViews() {
        commentAdapter = CommentAdapter(comments)
        commentRecyclerView.adapter = commentAdapter
        commentRecyclerView.setHasFixedSize(true)

        errorView.setListener(object : ErrorView.ErrorCallBack {
            override fun onRetryClick() {
                errorView.showLoading()
                getComments()
            }
        })
    }

    private fun getComments() {
        viewModel.getComments().observe(this, Observer {
            if(it?.status == ResponseStatus.SUCCESS && !it.data.isNullOrEmpty()) {
                comments.clear()
                comments.addAll(it.data)
            }

            getLocallySavedComments()
        })
    }

    private fun getLocallySavedComments() {
        if(podCast?.id != null) {
            viewModel.getLocallySavedComments(podCast?.id.toString()).observe(this, Observer {
                if(!it.data.isNullOrEmpty()) {
                    comments.addAll(it.data)
                    commentAdapter?.notifyDataSetChanged()
                    errorView.hideLoading()
                } else if(comments.isNotEmpty()) {
                    commentAdapter?.notifyDataSetChanged()
                    errorView.hideLoading()
                } else {
                    errorView.showEmptyView()
                }
            })
        }
    }

    private fun saveComment(comment: Comment) {
        if(podCast?.id != null) {
            comment.id = podCast?.id
            comment.child_count = 0
            comment.like_count = 0
            comment.share_count = 0
            comment.post_date = Utils.getCurrentTimeStamp()
            comment.episode = Episode()
            comment.episode?.title = "Fantastic Episode"
            comment.episode?.image_url = "https://upload.wikimedia.org/wikipedia/en/b/ba/Once_Upon_a_Time_in_Mumbaai.jpg"
            comment.user = User()
            comment.user?.name = "Sultan Mirza"
            comment.user?.image_url = "https://atiqulalam.files.wordpress.com/2015/02/alam1234.jpg?w=640"

            viewModel.saveComment(comment).observe(this, Observer {
                if(it?.data == true) {
                    comments.add(comment)
                    commentAdapter?.notifyDataSetChanged()
                }
            })
        }
    }

    @Subscribe
    fun onCommentEvent(event: Comment) {
        saveComment(event)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}
