package com.noice.ui.view

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.noice.R
import com.noice.model.Banner
import com.noice.ui.adapter.BannerAdapter
import kotlinx.android.synthetic.main.banner_view_layout.view.*
import java.util.*

/**
 * Created By Atiq
 */
class BannerView : FrameLayout {

    private var currentPage = 0
    private lateinit var swipeTimer: Timer
    private lateinit var adapter: BannerAdapter
    private var bannerInitialized = false

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
        inflate(context, R.layout.banner_view_layout, this)
        adapter = BannerAdapter(context)
        bannerViewPager?.adapter = adapter
    }

    fun setData(banners: ArrayList<Banner>){

        checkSwipeTimerInitialized()

        if(!bannerInitialized) {
            bannerInitialized = true
            val snapHelper =  PagerSnapHelper()
            snapHelper.attachToRecyclerView(bannerViewPager)
            indicator.attachToRecyclerView(bannerViewPager, snapHelper)
            adapter.registerAdapterDataObserver(indicator.adapterDataObserver)

            bannerViewPager.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    currentPage = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                }
            })
        }

        adapter.addData(banners)

        val update = Runnable {
            if (currentPage == banners.size) {
                currentPage = 0
            }
            bannerViewPager.smoothScrollToPosition(currentPage++)
        }

        swipeTimer = Timer()
        swipeTimer.schedule(object : TimerTask() {
            override fun run() {
                Handler(Looper.getMainLooper()).post(update)
            }
        }, 1000, 1000)

        indicator.visibility = if (banners.size > 1) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        checkSwipeTimerInitialized()
    }

    private fun checkSwipeTimerInitialized(){
        if(::swipeTimer.isInitialized){
            swipeTimer.cancel()
        }
    }
}