package com.mykuyauserapp.network.api

import com.mykuyauserapp.network.model.ProfileResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface ProfileApi {
    @GET("profile")
    fun getProfile(): Single<ProfileResponse>
}