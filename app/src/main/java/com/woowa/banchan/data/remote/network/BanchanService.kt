package com.woowa.banchan.data.remote.network

import com.woowa.banchan.data.remote.dto.DetailResponse
import com.woowa.banchan.data.remote.dto.SectionResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface BanchanService {

    @GET("onban/best")
    suspend fun getPlan(): Response<SectionResponse>

    @GET("onban/main")
    suspend fun getMainDish(): Response<SectionResponse>

    @GET("onban/soup")
    suspend fun getSoup(): Response<SectionResponse>

    @GET("onban/side")
    suspend fun getSide(): Response<SectionResponse>

    @GET("onban/detail{detail_hash}")
    suspend fun getDetail(@Path("detail_hash") detailHash: String): Response<DetailResponse>
}