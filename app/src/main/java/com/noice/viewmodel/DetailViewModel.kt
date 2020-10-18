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

class DetailViewModel : ViewModel() {

    private val repository = DetailRepository()

    fun getComments() = repository.getComments()

    fun getEpisodes() = repository.getEpisodes()

    fun getIsSubscribed(podCastId: String) = liveData(Dispatchers.IO) {
        val str = NoiceApplication.sharedNoicePref.getSubscribedPodCasts()
        if(str.isNotEmpty()) {
            val listType = object : TypeToken<ArrayList<String>>() {}.type
            val subscribedPodCasts : List<String> = Gson().fromJson(str, listType)
            emit(Resource.success(subscribedPodCasts.contains(podCastId)))
        } else {
            emit(Resource.success(false))
        }
    }

    fun subscribe(podCastId: String) = liveData(Dispatchers.IO) {
        val str = NoiceApplication.sharedNoicePref.getSubscribedPodCasts()
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
        NoiceApplication.sharedNoicePref.saveSubscribed(Gson().toJson(subscribedPodCasts))
    }

    fun saveComment(comment: Comment) = liveData(Dispatchers.IO) {
        val str = NoiceApplication.sharedNoicePref.getComments()
        val comments = ArrayList<Comment>()
        if(str.isNotEmpty()) {
            val listType = object : TypeToken<ArrayList<Comment>>() {}.type
            val list : ArrayList<Comment> = Gson().fromJson(str, listType)
            comments.addAll(list)
        }

        comments.add(comment)
        NoiceApplication.sharedNoicePref.saveComments(Gson().toJson(comments))
        emit(Resource.success(true))
    }

    fun saveChildComment(comment: Comment,commentId:String) = liveData(Dispatchers.IO) {
        val str = NoiceApplication.sharedNoicePref.getChildComments(commentId)
        val commentsList = ArrayList<Comment>()
        if(str.isNotEmpty()) {
            val listType = object : TypeToken<ArrayList<Comment>>() {}.type
            val list : ArrayList<Comment> = Gson().fromJson(str, listType)
            commentsList.addAll(list)
        }

        commentsList.add(comment)
        NoiceApplication.sharedNoicePref.saveChildComments(Gson().toJson(commentsList),commentId)
        emit(Resource.success(true))
    }


    fun getGetSavedChildComments(commentId:String) = liveData(Dispatchers.IO) {
        val str = NoiceApplication.sharedNoicePref.getChildComments(commentId)
        if(str.isNotEmpty()) {
            val listType = object : TypeToken<ArrayList<Comment>>() {}.type
            val list : ArrayList<Comment> = Gson().fromJson(str, listType)
            emit(Resource.success(list))
            return@liveData
        }

        emit(Resource.success(ArrayList()))
    }

    fun getGetSavedComments() = liveData(Dispatchers.IO) {
        val str = NoiceApplication.sharedNoicePref.getComments()
        if(str.isNotEmpty()) {
            val listType = object : TypeToken<ArrayList<Comment>>() {}.type
            val list : ArrayList<Comment> = Gson().fromJson(str, listType)
            emit(Resource.success(list))
            return@liveData
        }

        emit(Resource.success(ArrayList()))
    }
}