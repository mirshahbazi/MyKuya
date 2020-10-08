package com.mykuyauserapp.service

import com.mykuyauserapp.service.contract.ServicePresenterImpl
import com.mykuyauserapp.service.notifier.PublisherServiceNotifier
import com.mykuyauserapp.service.notifier.ServiceNotifier
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
val serviceModule = module {
    factory { ServicePresenterImpl(get(), get(), get(), get(), get(), get()) }
    single<ServiceNotifier> { PublisherServiceNotifier() }
}