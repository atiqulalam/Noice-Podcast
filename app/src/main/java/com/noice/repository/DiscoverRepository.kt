package com.noice.repository

import androidx.lifecycle.MutableLiveData
import com.noice.NoiceApplication
import com.noice.model.Banner
import com.noice.model.ResponseList
import com.noice.rest.CustomError
import com.noice.rest.NetworkRequests
import com.noice.rest.RestInterface
import com.noice.utils.Resource

class DiscoverRepository {

    fun getBanners() : MutableLiveData<Resource<List<Banner>>> {
        val mutableData = MutableLiveData<Resource<List<Banner>>>()

        NoiceApplication.doServerCall({NetworkRequests.getBanners()},
            object : RestInterface<ResponseList<Banner>> {

            override fun onCustomError(e: CustomError) {
                mutableData.postValue(Resource.error(e.message, null))
            }

            override fun onError(e: Throwable) {
                mutableData.postValue(Resource.error(Resource.GENERIC_ERROR, null))
            }

            override fun onSuccess(data: ResponseList<Banner>) {
                mutableData.postValue(Resource.success(data.data))
            }
        })

        return mutableData
    }
}