package com.noice.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.noice.model.Banner
import com.noice.ui.fragment.CommentsFragment
import com.noice.ui.fragment.EpisodesFragment
import com.noice.ui.fragment.DetailsFragment

class DetailPagerAdapter(fm: FragmentManager, private val podCast: Banner) : FragmentPagerAdapter(fm) {

    private val titles = arrayListOf("Episodes", "Details", "Comments")

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = EpisodesFragment.newInstance(podCast)
            }
            1 -> {
                fragment = DetailsFragment.newInstance(podCast)
            }
            2 -> {
                fragment = CommentsFragment.newInstance(podCast)
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