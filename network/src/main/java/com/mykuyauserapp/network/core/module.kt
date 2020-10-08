package com.mykuyauserapp.network.core

import com.mykuyauserapp.network.api.NewsApi
import com.mykuyauserapp.network.api.ProfileApi
import com.mykuyauserapp.network.api.ServiceApi
import com.mykuyauserapp.network.fakeapi.FakeNewsApi
import com.mykuyauserapp.network.fakeapi.FakeProfileApi
import com.mykuyauserapp.network.fakeapi.FakeServiceApi
import org.koin.dsl.module

val networkModule = module {
    single<NewsApi> { FakeNewsApi() }
    single<ProfileApi> { FakeProfileApi() }
    single<ServiceApi> { FakeServiceApi() }
}