package com.noice.repository

import androidx.lifecycle.MutableLiveData
import com.noice.NoiceApplication
import com.noice.model.Comment
import com.noice.model.Episode
import com.noice.model.ResponseList
import com.noice.rest.CustomError
import com.noice.rest.NetworkRequests
import com.noice.rest.RestInterface
import com.noice.utils.Resource

class DetailRepository {

    fun getComments() : MutableLiveData<Resource<List<Comment>>> {

        val mutableData = MutableLiveData<Resource<List<Comment>>>()

        NoiceApplication.doServerCall({ NetworkRequests.getComments()},
            object : RestInterface<ResponseList<Comment>> {

                override fun onCustomError(e: CustomError) {
                    mutableData.postValue(Resource.error(e.message, null))
                }

                override fun onError(e: Throwable) {
                    mutableData.postValue(Resource.error(Resource.GENERIC_ERROR, null))
                }

                override fun onSuccess(data: ResponseList<Comment>) {
                    mutableData.postValue(Resource.success(data.data))
                }
            })

        return mutableData
    }

    fun getEpisodes() : MutableLiveData<Resource<List<Episode>>> {

        val mutableData = MutableLiveData<Resource<List<Episode>>>()

        NoiceApplication.doServerCall({ NetworkRequests.getEpisodes()},
            object : RestInterface<ResponseList<Episode>> {

                override fun onCustomError(e: CustomError) {
                    mutableData.postValue(Resource.error(e.message, null))
                }

                override fun onError(e: Throwable) {
                    mutableData.postValue(Resource.error(Resource.GENERIC_ERROR, null))
                }

                override fun onSuccess(data: ResponseList<Episode>) {
                    mutableData.postValue(Resource.success(data.data))
                }
            })

        return mutableData
    }
}