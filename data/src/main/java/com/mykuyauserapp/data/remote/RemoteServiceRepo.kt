package com.mykuyauserapp.data.remote

import io.reactivex.rxjava3.core.Observable
import com.mykuyauserapp.data.repo.LatLng
import com.mykuyauserapp.data.repo.Service
import com.mykuyauserapp.data.repo.ServiceRepo
import com.mykuyauserapp.data.toPromotions
import com.mykuyauserapp.data.toServices
import com.mykuyauserapp.network.api.ServiceApi
/**
 * *
 * *          ____  ____ _____ ___   ____
 * *         | \ \ / / |/ _  || \ \ / / |
 * *         | |\ V /| | (_| || |\ V /| |
 * *         |_| \_/ |_|\__,_||_| \_/ |_|
 * *
 * Created by Mohammad Ali Mirshahbazi(MAM)
 */
class RemoteServiceRepo(
    private val serviceApi: ServiceApi
) : ServiceRepo {
    override fun getFeaturedServices(location: LatLng): Observable<List<Service>> =
        serviceApi.getPromotions(location.latitude, location.longitude).toObservable().toPromotions()

    override fun getAllServices(location: LatLng): Observable<List<Service>> =
        serviceApi.getAllServices(location.latitude, location.longitude).toObservable().toServices()
}