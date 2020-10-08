package com.mykuyauserapp.data.repo

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
/**
 * *
 * *          ____  ____ _____ ___   ____
 * *         | \ \ / / |/ _  || \ \ / / |
 * *         | |\ V /| | (_| || |\ V /| |
 * *         |_| \_/ |_|\__,_||_| \_/ |_|
 * *
 * Created by Mohammad Ali Mirshahbazi(MAM)
 */
interface LocationRepo {
    fun getCity(location: LatLng): Observable<String>
    fun updateLocation(location: LatLng): Completable
    fun getLocation(): Observable<LatLng>
}