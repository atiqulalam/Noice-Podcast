package com.noice.repository

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.noice.NoiceApplication
import com.noice.model.Banner
import com.noice.model.Comment
import com.noice.model.ResponseList
import com.noice.rest.CustomError
import com.noice.rest.NetworkRequests
import com.noice.rest.RestInterface
import com.noice.utils.Resource

class DiscoverRepository {

    fun getBanners() : MutableLiveData<Resource<List<Banner>>> {
        val mutableData = MutableLiveData<Resource<List<Banner>>>()

        val str = NoiceApplication.sharedNoicePref.getBanners()
        if(str.isNotEmpty()) {
            val listType = object : TypeToken<ArrayList<Banner>>() {}.type
            val list: ArrayList<Banner> = Gson().fromJson(str, listType)
            mutableData.postValue(Resource.success(list))
        }

        NoiceApplication.doServerCall({NetworkRequests.getBanners()},
            object : RestInterface<ResponseList<Banner>> {

            override fun onCustomError(e: CustomError) {
                if (str.isNullOrEmpty())
                mutableData.postValue(Resource.error(e.message, null))
            }

            override fun onError(e: Throwable) {
                if (str.isNullOrEmpty())
                mutableData.postValue(Resource.error(Resource.GENERIC_ERROR, null))
            }

            override fun onSuccess(data: ResponseList<Banner>) {
                NoiceApplication.sharedNoicePref.saveBanners(Gson().toJson(data.data))
                mutableData.postValue(Resource.success(data.data))
            }
        })

        return mutableData
    }
}