package com.noice.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.noice.model.Banner
import com.noice.ui.fragment.CommentsFragment
import com.noice.ui.fragment.EpisodesFragment

class DetailPagerAdapter(fm: FragmentManager, private val banner: Banner) : FragmentPagerAdapter(fm) {

    private val titles = arrayListOf("Episodes", "Comments")

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = EpisodesFragment.newInstance(banner)
            }
            1 -> {
                fragment = CommentsFragment.newInstance(banner)
            }
        }
        return fragment!!
    }

    override fun getCount(): Int {
        return titles.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }
}