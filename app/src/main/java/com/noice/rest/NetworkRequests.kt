package com.noice.rest

object NetworkRequests {
    private var request: ApiInterface? = null

    suspend fun getBanners() = request?.getBanners()

 //   suspend fun getPodCasts() = request?.getPodCasts()

    suspend fun getEpisodes() = request?.getEpisodes()

    suspend fun getComments() = request?.getComments()

    init {
        request = RestClient.createService(ApiInterface::class.java)
    }
}