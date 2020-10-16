package com.noice.utils

import android.content.Context
import android.content.SharedPreferences
import com.noice.helper.Constants

class NoicePref(ctx: Context) {
    private var sharedPref : SharedPreferences = ctx.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val SUBSCRIBERS = "SUBSCRIBERS"
        private const val COMMENTS = "COMMENTS"
        private const val BANNERS = "BANNERS"
        private const val EPISODS = "EPISODS"
    }

    fun saveSubscribed(string: String) {
        val editor = sharedPref.edit()
        editor.putString(SUBSCRIBERS, string)
        editor.apply()
    }

    fun saveComments(string: String) {
        val editor = sharedPref.edit()
        editor.putString(COMMENTS, string)
        editor.apply()
    }
    fun saveBanners(string: String) {
        val editor = sharedPref.edit()
        editor.putString(BANNERS, string)
        editor.apply()
    }

    fun saveEpisods(string: String) {
        val editor = sharedPref.edit()
        editor.putString(EPISODS, string)
        editor.apply()
    }

    fun getSubscribedPodCasts() : String {
        return sharedPref.getString(SUBSCRIBERS, "") ?: ""
    }

    fun getComments() : String {
        return sharedPref.getString(COMMENTS, "") ?: ""
    }

    fun getBanners() : String {
        return sharedPref.getString(BANNERS, "") ?: ""
    }

    fun getEpisods() : String {
        return sharedPref.getString(EPISODS, "") ?: ""
    }
}