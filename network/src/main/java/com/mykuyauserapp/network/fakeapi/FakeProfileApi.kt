package com.mykuyauserapp.network.fakeapi

import io.reactivex.rxjava3.core.Single
import com.mykuyauserapp.network.model.Profile
import com.mykuyauserapp.network.api.ProfileApi
import com.mykuyauserapp.network.model.ProfileResponse
import java.util.concurrent.TimeUnit

class FakeProfileApi : ProfileApi {
    override fun getProfile(): Single<ProfileResponse> = Single.just(
        ProfileResponse(
        Profile("1", "Mohammad", "mirshahbazi")
    )
    ).delay(100, TimeUnit.MILLISECONDS)
}