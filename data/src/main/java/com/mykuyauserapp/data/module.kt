package com.mykuyauserapp.data

import com.mykuyauserapp.data.remote.*
import com.mykuyauserapp.data.repo.*
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
/**
 * *
 * *          ____  ____ _____ ___   ____
 * *         | \ \ / / |/ _  || \ \ / / |
 * *         | |\ V /| | (_| || |\ V /| |
 * *         |_| \_/ |_|\__,_||_| \_/ |_|
 * *
 * Created by Mohammad Ali Mirshahbazi(MAM)
 */
val dataModule = module {
    single<NewsRepo> { RemoteNewsRepo(get()) }
    single<ServiceRepo> { RemoteServiceRepo(get()) }
    single<UserRepo> { RemoteUserRepo(get()) }
    single<LocationRepo> { RemoteLocationRepo(get()) }
    single<LocalStorage<LatLng>> { PrefLocationStorage(androidContext()) }
}