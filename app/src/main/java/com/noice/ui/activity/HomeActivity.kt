package com.noice.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.noice.R
import com.noice.ui.fragment.*
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    private lateinit var discoverFragment : DiscoverFragment
    private lateinit var communityFragment : CommunityFragment
    private lateinit var libraryFragment : LibraryFragment
    private lateinit var personalFragment : PersonalFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initializeFragments()

       navView.setOnNavigationItemSelectedListener { item ->
           when (item.itemId) {
               R.id.navigation_discover -> {
                   Log.d("Clicked bottom menu - ","navigation_discover" )
                   supportFragmentManager.beginTransaction().show(discoverFragment).commit()
                   supportFragmentManager.beginTransaction().hide(communityFragment).commit()
                   supportFragmentManager.beginTransaction().hide(libraryFragment).commit()
                   supportFragmentManager.beginTransaction().hide(personalFragment).commit()
               }
               R.id.navigation_community -> {
                   Log.d("Clicked bottom menu - ","navigation_community" )
                   supportFragmentManager.beginTransaction().hide(discoverFragment).commit()
                   supportFragmentManager.beginTransaction().show(communityFragment).commit()
                   supportFragmentManager.beginTransaction().hide(libraryFragment).commit()
                   supportFragmentManager.beginTransaction().hide(personalFragment).commit()
               }
               R.id.navigation_library -> {
                   Log.d("Clicked bottom menu - ","navigation_library" )
                   supportFragmentManager.beginTransaction().hide(discoverFragment).commit()
                   supportFragmentManager.beginTransaction().hide(communityFragment).commit()
                   supportFragmentManager.beginTransaction().show(libraryFragment).commit()
                   supportFragmentManager.beginTransaction().hide(personalFragment).commit()
               }
               R.id.navigation_personal -> {
                   Log.d("Clicked bottom menu - ","navigation_personal" )
                   supportFragmentManager.beginTransaction().hide(discoverFragment).commit()
                   supportFragmentManager.beginTransaction().hide(communityFragment).commit()
                   supportFragmentManager.beginTransaction().hide(libraryFragment).commit()
                   supportFragmentManager.beginTransaction().show(personalFragment).commit()
               }
           }
           true
       }
    }

    private fun initializeFragments() {
        if (!::discoverFragment.isInitialized){
            discoverFragment = DiscoverFragment()
        }
        if (!::communityFragment.isInitialized){
            communityFragment = CommunityFragment()
        }
        if (!::libraryFragment.isInitialized){
            libraryFragment = LibraryFragment()
        }
        if (!::personalFragment.isInitialized){
            personalFragment = PersonalFragment()
        }

        supportFragmentManager.beginTransaction().add(R.id.idFrameContent, discoverFragment, "1").show(discoverFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.idFrameContent, communityFragment, "2").hide(communityFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.idFrameContent, libraryFragment, "3").hide(libraryFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.idFrameContent, personalFragment, "4").hide(personalFragment).commit()

    }
}
