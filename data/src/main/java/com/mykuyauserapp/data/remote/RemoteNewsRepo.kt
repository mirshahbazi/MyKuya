package com.mykuyauserapp.data.remote

import io.reactivex.rxjava3.core.Observable
import com.mykuyauserapp.data.repo.News
import com.mykuyauserapp.data.repo.NewsRepo
import com.mykuyauserapp.data.toNews
import com.mykuyauserapp.network.api.NewsApi
/**
 * *
 * *          ____  ____ _____ ___   ____
 * *         | \ \ / / |/ _  || \ \ / / |
 * *         | |\ V /| | (_| || |\ V /| |
 * *         |_| \_/ |_|\__,_||_| \_/ |_|
 * *
 * Created by Mohammad Ali Mirshahbazi(MAM)
 */
class RemoteNewsRepo(
    private val newsApi: NewsApi
) : NewsRepo {
    override fun getNews(): Observable<List<News>> = newsApi.getNews().toObservable().toNews()
}