package com.mykuyauserapp.data.remote

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import com.mykuyauserapp.data.repo.LatLng
import com.mykuyauserapp.data.repo.LocalStorage
import com.mykuyauserapp.data.repo.LocationRepo
/**
 * *
 * *          ____  ____ _____ ___   ____
 * *         | \ \ / / |/ _  || \ \ / / |
 * *         | |\ V /| | (_| || |\ V /| |
 * *         |_| \_/ |_|\__,_||_| \_/ |_|
 * *
 * Created by Mohammad Ali Mirshahbazi(MAM)
 */
class RemoteLocationRepo(
    private val localStorage: LocalStorage<LatLng>
) : LocationRepo {
    private val locations = listOf(
        Pair(
            LatLng(35.815283, 50.986490), "Iran,Alborz,Karaj"
        ),
        Pair(
            LatLng(35.815283, 50.986490), "Iran,Alborz,Karaj"
        ),
        Pair(
            LatLng(35.815283, 50.986490), "Iran,Alborz,Karaj"
        ),
        Pair(
            LatLng(35.815283, 50.986490), "Iran,Alborz,Karaj"
        ),
        Pair(
            LatLng(35.815283, 50.986490), "Iran,Alborz,Karaj"
        )
    )
    override fun getCity(location: LatLng): Observable<String> = Observable.defer {
        val minDistance = locations.minBy { it.first.distance(location) }
        return@defer Observable.just(minDistance?.second)
    }
    override fun updateLocation(location: LatLng): Completable = Completable.defer {
        localStorage.save(location)
        return@defer Completable.complete()
    }

    override fun getLocation(): Observable<LatLng> = localStorage.data


    private fun LatLng.distance(
        location: LatLng
    ): Double {
        val theta = longitude - location.longitude
        var dist = (Math.sin(deg2rad(latitude))
                * Math.sin(deg2rad(location.latitude))
                + (Math.cos(deg2rad(latitude))
                * Math.cos(deg2rad(location.latitude))
                * Math.cos(deg2rad(theta))))
        dist = Math.acos(dist)
        dist = rad2deg(dist)
        dist *= 60 * 1.1515
        return dist
    }

    private fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    private fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }
}