package com.mykuyauserapp.network.fakeapi

import io.reactivex.rxjava3.core.Single
import com.mykuyauserapp.network.model.News
import com.mykuyauserapp.network.api.NewsApi
import com.mykuyauserapp.network.model.NewsResponse
import java.util.concurrent.TimeUnit

class FakeNewsApi : NewsApi {
    override fun getNews(): Single<NewsResponse> = Single.just(
        NewsResponse(
        listOf(
            News("1", "How to use the app", "Getting access to on-demand", "header27"),
            News("2", "List your service on MyKuya", "Do you offer manpower", "header29")
        )
    )
    ).delay(2000, TimeUnit.MILLISECONDS)
}