package com.mykuyauserapp.network.api

import com.mykuyauserapp.network.model.PromotionResponse
import com.mykuyauserapp.network.model.ServicesResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceApi  {
    @GET("services/promotion")
    fun getPromotions(@Query("latitude") latitude: Double, @Query("longitude") longitude: Double): Single<PromotionResponse>

    @GET("services")
    fun getAllServices(@Query("latitude") latitude: Double, @Query("longitude") longitude: Double): Single<ServicesResponse>

}