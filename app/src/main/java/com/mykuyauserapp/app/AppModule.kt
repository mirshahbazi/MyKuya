package com.mykuyauserapp.app
import com.mykuyauserapp.ui.main.MainPresenterImpl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import com.mykuyauserapp.base.SchedulersCore
import io.reactivex.rxjava3.schedulers.Schedulers as rxscheduler
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
val mainModule = module {
    single { SchedulersCore(AndroidSchedulers.mainThread(), rxscheduler.io()) }
    factory { MainPresenterImpl(get(), get(), get()) }
}