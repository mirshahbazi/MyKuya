package com.mykuyauserapp.mykuya

import androidx.lifecycle.Lifecycle
import com.mykuyauserapp.ui.main.MainPresenterImpl
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.TestScheduler
import com.mykuyauserapp.base.RxRule
import com.mykuyauserapp.base.SchedulersCore
import com.mykuyauserapp.data.repo.LatLng
import com.mykuyauserapp.data.repo.LocationRepo
import com.mykuyauserapp.service.notifier.ServiceNotifier
import org.junit.After
import org.junit.Before

import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeUnit
/**
 * *
 * *          ____  ____ _____ ___   ____
 * *         | \ \ / / |/ _  || \ \ / / |
 * *         | |\ V /| | (_| || |\ V /| |
 * *         |_| \_/ |_|\__,_||_| \_/ |_|
 * *
 * Created by Mohammad Ali Mirshahbazi(MAM)
 */
class MainPresenterTest {

    private val scheduler = TestScheduler()
    @get:Rule
    val rule = RxRule(scheduler)

    @RelaxedMockK
    lateinit var locationRepo: LocationRepo

    @RelaxedMockK
    lateinit var serviceNotifier: ServiceNotifier

    @RelaxedMockK
    lateinit var lifecycle: Lifecycle

    fun createPresenter() = MainPresenterImpl(locationRepo, serviceNotifier, SchedulersCore(scheduler, scheduler))

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `when location repo returns default location, then loadMapScreen should called`() {
        val view = mockk<MainPresenterImpl.MainView>(relaxed = true)
        val presenter = createPresenter()
        every {
            locationRepo.getLocation()
        } returns Observable.just(LatLng(1.0, 1.0))
        presenter.attachView(view, lifecycle)
        scheduler.advanceTimeBy(1000, TimeUnit.MILLISECONDS)
        verify {
            view.loadMapScreen()
        }
    }

    @Test
    fun `when location repo returns some other location, then loadMapScreen should not get called`() {
        val view = mockk<MainPresenterImpl.MainView>(relaxed = true)
        val presenter = createPresenter()
        every {
            locationRepo.getLocation()
        } returns Observable.just(LatLng(2.0, 2.0))
        presenter.attachView(view, lifecycle)
        scheduler.advanceTimeBy(1000, TimeUnit.MILLISECONDS)
        verify(exactly = 0) {
            view.loadMapScreen()
        }
    }

    @Test
    fun `when service notifier get called, then loadMap should get called`() {
        val view = mockk<MainPresenterImpl.MainView>(relaxed = true)
        val presenter = createPresenter()
        every {
            serviceNotifier.services
        } returns Observable.just(ServiceNotifier.Service.Map(LatLng(12.0, 12.0)))
        presenter.attachView(view, lifecycle)
        scheduler.advanceTimeBy(1000, TimeUnit.MILLISECONDS)
        verify {
            view.loadMap(LatLng(12.0, 12.0))
        }
    }
}