package com.mykuyauserapp.app

import android.app.Application
import com.mykuyauserapp.data.dataModule
import com.mykuyauserapp.map.mapModule
import com.mykuyauserapp.network.core.networkModule
import com.mykuyauserapp.service.serviceModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
/**
 * *
 * *          ____  ____ _____ ___   ____
 * *         | \ \ / / |/ _  || \ \ / / |
 * *         | |\ V /| | (_| || |\ V /| |
 * *         |_| \_/ |_|\__,_||_| \_/ |_|
 * *
 * Created by Mohammad Ali Mirshahbazi(MAM)
 */
class AppController : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(listOf(
                mainModule,
                serviceModule,
                networkModule,
                dataModule,
                mapModule
            ))
        }
    }
}