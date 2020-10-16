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
import com.noice.viewmodel.DetailViewModel
import com.noice.utils.ResponseStatus
import com.noice.utils.Utils
import com.noice.ui.view.ErrorView
import kotlinx.android.synthetic.main.fragment_comments.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
/**
 * Created By Atiq
 */
class CommentsFragment : Fragment() {

    companion object {
        private const val BANNER = "BANNER"

        fun newInstance(banner: Banner) : CommentsFragment {
            val fragment = CommentsFragment()
            val bundle = Bundle()
            bundle.putParcelable(BANNER, banner)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var banner: Banner? = null
    private var commentAdapter: CommentAdapter? = null
    private lateinit var viewModel: DetailViewModel
    private var commentList = ArrayList<Comment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        banner = arguments?.getParcelable(BANNER)
        EventBus.getDefault().register(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        return inflater.inflate(R.layout.fragment_comments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

        if(banner?.id != null) {
            getComments()
        }
    }

    private fun initViews() {
        commentAdapter = CommentAdapter(commentList)
        rvComment.adapter = commentAdapter
        rvComment.setHasFixedSize(true)

        errorView.setListener(object : ErrorView.ErrorCallBack {
            override fun onRetryClick() {
                errorView.showLoading()
                getComments()
            }
        })
    }

    private fun getComments() {
        getLocallySavedComments()
        viewModel.getComments().observe(this, Observer {
            if(it?.status == ResponseStatus.SUCCESS && !it.data.isNullOrEmpty()) {
                errorView.hideLoading()
                commentList.addAll(it.data)
                commentAdapter?.notifyDataSetChanged()
            }
        })
    }

    /**
     * Initially display local data, and after API response merged with local data display to user
     */

    private fun getLocallySavedComments() {
        if(banner?.id != null) {
            viewModel.getGetSavedComments().observe(this, Observer {
                if(!it.data.isNullOrEmpty()) {
                    commentList.clear()
                    commentList.addAll(it.data)
                    commentAdapter?.notifyDataSetChanged()
                    errorView.hideLoading()
                } else if(commentList.isNotEmpty()) {
                    commentAdapter?.notifyDataSetChanged()
                    errorView.hideLoading()
                } else {
                    errorView.showEmptyView()
                }
            })
        }
    }

    private fun saveComment(comment: Comment) {
        if(banner?.id != null) {
            comment.id = banner?.id
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
                    commentList.add(comment)
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
