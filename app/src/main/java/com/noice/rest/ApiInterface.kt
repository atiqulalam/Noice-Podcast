package com.noice.rest

import com.noice.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("banners")
    suspend fun getBanners(): Response<ResponseList<Banner>>

    @GET("comments")
    suspend fun getComments(): Response<ResponseList<Comment>>

    @GET("episodes")
    suspend fun getEpisodes(): Response<ResponseList<Episode>>
}