package com.noice.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.noice.R
import com.noice.model.Banner
import com.noice.model.Episode
import com.noice.ui.adapter.EpisodeAdapter
import com.noice.ui.view.ErrorView
import com.noice.utils.ResponseStatus
import com.noice.viewmodel.PodCastDetailViewModel

import kotlinx.android.synthetic.main.fragment_episodes.*
/**
 * Created By Atiq
 */
class EpisodesFragment : Fragment() {

    companion object {
        private const val BANNER_DATA = "BANNER_DATA"

        fun newInstance(banner: Banner) : EpisodesFragment {
            val fragment = EpisodesFragment()
            val bundle = Bundle()
            bundle.putParcelable(BANNER_DATA, banner)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var banner: Banner? = null
    private var episodeAdapter: EpisodeAdapter? = null
    private var episodes = ArrayList<Episode>()
    private lateinit var viewModel: PodCastDetailViewModel
    private lateinit var ctx: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        banner = arguments?.getParcelable(BANNER_DATA)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(PodCastDetailViewModel::class.java)
        return inflater.inflate(R.layout.fragment_episodes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        errorView.showLoading()

        initViews()

        getEpisodes()
    }

    private fun initViews() {
        episodeAdapter = EpisodeAdapter(episodes)
        episodeRecyclerView.adapter = episodeAdapter

        episodesCount.text = ctx.getString(R.string.episodes, episodes.size)

        errorView.setListener(object : ErrorView.ErrorCallBack {
            override fun onRetryClick() {
                errorView.showLoading()
                getEpisodes()
            }
        })
    }

    private fun getEpisodes() {
        if(banner?.id == null)
            return

        viewModel.getEpisodes().observe(this, Observer {
            if(it.status == ResponseStatus.SUCCESS) {
                if(!it.data.isNullOrEmpty()) {
                    episodes.clear()
                    episodes.addAll(it.data)
                    episodeAdapter?.notifyDataSetChanged()
                    episodesCount.text = ctx.getString(R.string.episodes, episodes.size)
                    errorView.hideLoading()
                } else {
                    errorView.showEmptyView()
                }
            } else {
                errorView.setErrorMessage()
            }
        })
    }
}