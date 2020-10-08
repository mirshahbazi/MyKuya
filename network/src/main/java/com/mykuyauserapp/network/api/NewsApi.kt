package com.mykuyauserapp.network.api

import com.mykuyauserapp.network.model.NewsResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface NewsApi {
    @GET("news")
    fun getNews(): Single<NewsResponse>
}