package com.noice.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.noice.utils.Resource
import com.noice.NoiceApplication
import com.noice.model.Comment
import com.noice.repository.DetailRepository
import kotlinx.coroutines.Dispatchers

class PodCastDetailViewModel : ViewModel() {

    private val repository = DetailRepository()

    fun getComments() = repository.getComments()

    fun getEpisodes() = repository.getEpisodes()

    fun getIsSubscribed(podCastId: String) = liveData(Dispatchers.IO) {
        val str = NoiceApplication.sharedPref.getSubscribedPodCasts()
        if(str.isNotEmpty()) {
            val listType = object : TypeToken<ArrayList<String>>() {}.type
            val subscribedPodCasts : List<String> = Gson().fromJson(str, listType)
            emit(Resource.success(subscribedPodCasts.contains(podCastId)))
        } else {
            emit(Resource.success(false))
        }
    }

    fun subscribe(podCastId: String) = liveData(Dispatchers.IO) {
        val str = NoiceApplication.sharedPref.getSubscribedPodCasts()
        val subscribedPodCasts = ArrayList<String>()
        if(str.isNotEmpty()) {
            val listType = object : TypeToken<ArrayList<String>>() {}.type
            val list : ArrayList<String> = Gson().fromJson(str, listType)
            subscribedPodCasts.addAll(list)
            if(subscribedPodCasts.contains(podCastId)) {
                subscribedPodCasts.remove(podCastId)
                emit(Resource.success(false))
            } else {
                subscribedPodCasts.add(podCastId)
                emit(Resource.success(true))
            }
        } else {
            subscribedPodCasts.add(podCastId)
            emit(Resource.success(true))
        }
        NoiceApplication.sharedPref.saveSubscribedPodCasts(Gson().toJson(subscribedPodCasts))
    }

    fun saveComment(comment: Comment) = liveData(Dispatchers.IO) {
        val str = NoiceApplication.sharedPref.getComments()
        val comments = ArrayList<Comment>()
        if(str.isNotEmpty()) {
            val listType = object : TypeToken<ArrayList<Comment>>() {}.type
            val list : ArrayList<Comment> = Gson().fromJson(str, listType)
            comments.addAll(list)
        }

        comments.add(comment)
        NoiceApplication.sharedPref.saveComments(Gson().toJson(comments))
        emit(Resource.success(true))
    }

    fun getLocallySavedComments(podCastId: String) = liveData(Dispatchers.IO) {
        val str = NoiceApplication.sharedPref.getComments()
        if(str.isNotEmpty()) {
            val listType = object : TypeToken<ArrayList<Comment>>() {}.type
            val list : ArrayList<Comment> = Gson().fromJson(str, listType)

            list.filter {
                it.id.toString() == podCastId
            }.let {
                emit(Resource.success(it))
                return@liveData
            }
        }

        emit(Resource.success(ArrayList()))
    }
}