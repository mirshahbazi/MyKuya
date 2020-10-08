package com.mykuyauserapp.map

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import com.mykuyauserapp.base.BasePresenterImpl
import com.mykuyauserapp.base.SchedulersCore
import com.mykuyauserapp.data.repo.LatLng
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
class MapPresenterImpl(
    private val locationRepo: LocationRepo,
    private val schedulersCore: SchedulersCore
) : BasePresenterImpl<MapPresenterImpl.MapView>() {

    private val locationUpdate = PublishSubject.create<LatLng>()
    override fun viewAttached() {
        locationUpdate.flatMap {
            locationRepo.updateLocation(it).subscribeOn(schedulersCore.io).andThen(Observable.just(Unit))
        }.observeOn(schedulersCore.main).subscribe({
            view()?.locationUpdated()
        }, {
            view()?.locationUpdateError(it)
        }).add()
    }

    fun submitLocation(location: LatLng) {
        locationUpdate.onNext(location)
    }

    interface MapView {
        fun locationUpdated()
        fun locationUpdateError(it: Throwable?)
    }
}