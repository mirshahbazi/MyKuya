package com.mykuyauserapp.ui.main

import com.mykuyauserapp.base.BasePresenterImpl
import com.mykuyauserapp.base.SchedulersCore
import com.mykuyauserapp.data.repo.LatLng
import com.mykuyauserapp.data.repo.LocationRepo
import com.mykuyauserapp.service.notifier.ServiceNotifier
/**
 * *
 * *          ____  ____ _____ ___   ____
 * *         | \ \ / / |/ _  || \ \ / / |
 * *         | |\ V /| | (_| || |\ V /| |
 * *         |_| \_/ |_|\__,_||_| \_/ |_|
 * *
 * Created by Mohammad Ali Mirshahbazi(MAM)
 */
class MainPresenterImpl(
    private val locationRepo: LocationRepo,
    private val serviceNotifier: ServiceNotifier,
    private val schedulersCore: SchedulersCore
) : BasePresenterImpl<MainPresenterImpl.MainView>() {

    override fun viewAttached() {
        locationRepo.getLocation().subscribeOn(schedulersCore.io)
            .observeOn(schedulersCore.main).subscribe({
            if (it == LatLng(1.0, 1.0)) {
                view()?.loadMapScreen()
            }
        }, {

        }).add()

        serviceNotifier.services.subscribeOn(schedulersCore.io)
            .observeOn(schedulersCore.main).subscribe {
                when(it) {
                    is ServiceNotifier.Service.Map -> view()?.loadMap(it.location)
                }
            }.add()
    }

    interface MainView {
        fun loadMapScreen()
        fun loadMap(location: LatLng)
    }
}