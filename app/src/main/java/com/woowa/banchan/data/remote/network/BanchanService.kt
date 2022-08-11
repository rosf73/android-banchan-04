package com.woowa.banchan.data.remote.network

import com.woowa.banchan.data.remote.dto.DetailResponse
import com.woowa.banchan.data.remote.dto.PlanResponse
import com.woowa.banchan.data.remote.dto.ProductsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface BanchanService {

    @GET("onban/best")
    suspend fun getPlan(): Response<PlanResponse>

    @GET("onban/main")
    suspend fun getMainDish(): Response<ProductsResponse>

    @GET("onban/soup")
    suspend fun getSoup(): Response<ProductsResponse>

    @GET("onban/side")
    suspend fun getSide(): Response<ProductsResponse>

    @GET("onban/detail{detail_hash}")
    suspend fun getDetail(@Path("detail_hash") detailHash: String): Response<DetailResponse>
}