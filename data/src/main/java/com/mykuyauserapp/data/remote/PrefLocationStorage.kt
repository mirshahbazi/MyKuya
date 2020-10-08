package com.mykuyauserapp.data.remote

import android.content.Context
import android.content.SharedPreferences
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import com.mykuyauserapp.data.repo.LatLng
import com.mykuyauserapp.data.repo.LocalStorage
/**
 * *
 * *          ____  ____ _____ ___   ____
 * *         | \ \ / / |/ _  || \ \ / / |
 * *         | |\ V /| | (_| || |\ V /| |
 * *         |_| \_/ |_|\__,_||_| \_/ |_|
 * *
 * Created by Mohammad Ali Mirshahbazi(MAM)
 */
class PrefLocationStorage(val context: Context) : LocalStorage<LatLng> {
    private var pref: SharedPreferences = context.getSharedPreferences("app", Context.MODE_PRIVATE)
    private val subject = BehaviorSubject.create<LatLng>()

    init {
        val lat = pref.getString("latitude", "1")
        val lng = pref.getString("longitude", "1")
        subject.onNext(LatLng(lat!!.toDouble(), lng!!.toDouble()))
    }
    override fun save(data: LatLng) {
        pref.edit().apply {
            putString("latitude", data.latitude.toString())
            putString("longitude", data.longitude.toString())
            apply()
        }
        subject.onNext(data)
    }

    override val data: Observable<LatLng>
        get() = subject
}