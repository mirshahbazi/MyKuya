package com.mykuyauserapp.service.contract

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.subjects.PublishSubject
import com.mykuyauserapp.base.BasePresenterImpl
import com.mykuyauserapp.base.Loadable
import com.mykuyauserapp.base.SchedulersCore
import com.mykuyauserapp.data.repo.*
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
class ServicePresenterImpl(
    private val serviceRepo: ServiceRepo,
    private val newsRepo: NewsRepo,
    private val userRepo: UserRepo,
    private val locationRepo: LocationRepo,
    private val serviceNotifier: ServiceNotifier,
    private val schedulersCore: SchedulersCore
): BasePresenterImpl<ServicePresenterImpl.ServiceView>() {

    private val refreshPublisher: PublishSubject<Unit> = PublishSubject.create()
    private val latestLocation = PublishSubject.create<Unit>()

    override fun viewAttached() {

        val refreshShare = refreshPublisher.share()
        val refreshLocation = Observable.combineLatest(refreshShare, locationRepo.getLocation(),
            BiFunction<Unit, LatLng, LatLng> { t1, t2 ->
                t2
            }).share()
        refreshLocation.flatMap {
            serviceRepo.getFeaturedServices(it)
                .subscribeOn(schedulersCore.io)
        }.observeOn(schedulersCore.main).subscribe({
            view()?.promotedServices(Loadable.Loaded(it))
        }, {
            view()?.promotedServices(Loadable.Failed(it))
        }).add()

        refreshLocation.flatMap {
            serviceRepo.getAllServices(it).subscribeOn(schedulersCore.io)
        }.observeOn(schedulersCore.main).subscribe ({
            view()?.allServices(Loadable.Loaded(it))
        }, {
            view()?.allServices(Loadable.Failed(it))
        }).add()

        refreshShare.flatMap {
            newsRepo.getNews().subscribeOn(schedulersCore.io).observeOn(schedulersCore.main)
        }.subscribe({
            view()?.updateNews(Loadable.Loaded(it))
        }, {
            view()?.updateNews(Loadable.Failed(it))
        }).add()
        userRepo.getProfile().subscribeOn(schedulersCore.io).observeOn(schedulersCore.main).subscribe({
            view()?.updateProfile(Loadable.Loaded(it))
        }, {
            view()?.updateProfile(Loadable.Failed(it))
        }).add()
        refreshLocation.flatMap {
            locationRepo.getCity(it)
        }.subscribeOn(schedulersCore.io).observeOn(schedulersCore.main).subscribe({
            view()?.updateCity(it)
        }, {
            it.printStackTrace()
        }).add()

        Observable.zip(latestLocation, locationRepo.getLocation(), BiFunction<Unit, LatLng, LatLng> { t1, t2 ->
            t2
        }).subscribeOn(schedulersCore.io).observeOn(schedulersCore.main).subscribe({
            serviceNotifier.selectService(ServiceNotifier.Service.Map(it))
        }, {
            it.printStackTrace()
        }).add()
        refreshPublisher.onNext(Unit)
    }

    fun refreshNews() {
        refreshPublisher.onNext(Unit)
    }

    fun requestMap() {
        latestLocation.onNext(Unit)
    }

    interface ServiceView {
        fun promotedServices(data: Loadable<List<Service>>)
        fun allServices(data: Loadable<List<Service>>)
        fun updateNews(data: Loadable<List<News>>)
        fun updateProfile(data: Loadable<User>)
        fun updateCity(name: String)
        fun loadMap(location: LatLng)
    }
}