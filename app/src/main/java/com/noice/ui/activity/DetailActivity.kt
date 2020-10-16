package com.noice.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.google.android.material.appbar.AppBarLayout
import com.noice.R
import com.noice.listener.AppBarStateListener
import com.noice.model.Banner
import com.noice.model.Comment
import com.noice.ui.adapter.DetailPagerAdapter
import com.noice.utils.ImageUtils
import com.noice.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import org.greenrobot.eventbus.EventBus

class DetailActivity : AppCompatActivity() {

    private var tabAdapter: DetailPagerAdapter? = null
    private var banner: Banner? = null
    private lateinit var viewModel : DetailViewModel
    private var isSubscribed = false

    companion object {
        private const val BANNER = "BANNER"

        fun start(ctx: Context, podCast: Banner) {
            val intent = Intent(ctx, DetailActivity::class.java)
            intent.putExtra(BANNER, podCast)
            ctx.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        setToolbar()

        getDataFromIntent()

        if(banner == null) {
            finish()
            return
        }

        initViews()

        getIsSubscribed()
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        toolbar.title = getString(R.string.show_info)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun getDataFromIntent() {
        if(intent.hasExtra(BANNER)) {
            banner = intent.getParcelableExtra(BANNER)
        }
    }

    private fun initViews() {
        viewPager.offscreenPageLimit = 2
        tabAdapter = DetailPagerAdapter(supportFragmentManager, banner!!)
        viewPager.adapter = tabAdapter
        tabLayout.setupWithViewPager(viewPager)


        appBarLayout.addOnOffsetChangedListener(object : AppBarStateListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
                if(state == State.COLLAPSED) {
                    toolbar.setBackgroundColor(ContextCompat.getColor(this@DetailActivity, R.color.colorPrimary))
                } else {
                    toolbar.setBackgroundColor(ContextCompat.getColor(this@DetailActivity, android.R.color.transparent))
                }
            }
        })

        viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                if(tabLayout.getTabAt(position)?.text.toString().equals("comments", true)) {
                    addCommentLayout.visibility = View.VISIBLE
                } else {
                    addCommentLayout.visibility = View.GONE
                }
            }
        })

        edtComment.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                if(edtComment.text.toString().isNotEmpty()) {
                    val comment = Comment()
                    comment.comment = edtComment.text.toString()
                    EventBus.getDefault().post(comment)
                    edtComment.setText("")
                }
            }
            false
        }

        if(banner?.image_url != null) {
            ImageUtils.setImageByUrlAndCache(this, podCastImage, banner?.image_url)
        }

        if(banner?.title != null) {
            txtTitle.text = banner?.title
        }

        if(banner?.subscriber != null) {
            txtBookMarkCount.text = banner?.subscriber.toString()
        }

        if(banner?.view != null) {
            txtViews.text = banner?.view.toString()
        }

        txtSubscribe.setOnClickListener {
            subscribe()
        }
    }

    private fun getIsSubscribed() {
        viewModel.getIsSubscribed(banner?.id.toString()).observe(this, Observer {
            isSubscribed = it?.data ?: false
            setSubscribeButton()
        })
    }

    private fun subscribe() {
        viewModel.subscribe(banner?.id.toString()).observe(this, Observer {
            isSubscribed = it?.data ?: false
            setSubscribeButton()
        })
    }

    private fun setSubscribeButton() {
        if(isSubscribed) {
            txtSubscribe.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bookmark_small,0,0,0)
            txtSubscribe.text = getString(R.string.subscribed)
        } else {
            txtSubscribe.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bookmark_unselected,0,0,0)
            txtSubscribe.text = getString(R.string.subscribe)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }

        return true
    }
}
