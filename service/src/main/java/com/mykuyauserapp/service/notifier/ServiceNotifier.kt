package com.mykuyauserapp.service.notifier

import io.reactivex.rxjava3.core.Observable
import com.mykuyauserapp.data.repo.LatLng
/**
 * *
 * *          ____  ____ _____ ___   ____
 * *         | \ \ / / |/ _  || \ \ / / |
 * *         | |\ V /| | (_| || |\ V /| |
 * *         |_| \_/ |_|\__,_||_| \_/ |_|
 * *
 * Created by Mohammad Ali Mirshahbazi(MAM)
 */
interface ServiceNotifier {
    fun selectService(service: Service)
    val services: Observable<Service>

    sealed class Service {
        data class Map(val location: LatLng): Service()
    }
}