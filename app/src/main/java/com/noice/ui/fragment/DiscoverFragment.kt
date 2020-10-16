package com.noice.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.noice.R
import com.noice.model.Banner
import com.noice.viewmodel.DiscoverViewModel
import com.noice.utils.ResponseStatus
import com.noice.ui.view.ErrorView
import kotlinx.android.synthetic.main.fragment_discover.*
/**
 * Created By Atiq
 */
class DiscoverFragment : Fragment() {

    private lateinit var discoverViewModel: DiscoverViewModel
    private lateinit var ctx: Context

    private var banners = ArrayList<Banner>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        discoverViewModel = ViewModelProvider(this).get(DiscoverViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_discover, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        getBanners()
    }

    private fun initViews() {
        errorView.setListener(object : ErrorView.ErrorCallBack {
            override fun onRetryClick() {
                showLoading()
                getBanners()
            }
        })
    }

    private fun getBanners() {
        showLoading()
        discoverViewModel.getBanners().observe(this, Observer {
            hideLoading()
            if(it?.status == ResponseStatus.SUCCESS && !it.data.isNullOrEmpty()) {
                banners.addAll(it.data)
                bannerView.setData(banners)
            }else {
                showError()
            }
        })
    }

    private fun hideLoading() {
        bannerView.visibility = View.VISIBLE
        errorView.hideLoading()
    }

    private fun showLoading() {
        bannerView.visibility = View.GONE
        errorView.showLoading()
    }

    private fun showError() {
        bannerView.visibility = View.GONE
        errorView.setErrorMessage()
    }
}