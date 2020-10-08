package com.mykuyauserapp.map

import androidx.lifecycle.Lifecycle
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.TestScheduler
import com.mykuyauserapp.base.RxRule
import com.mykuyauserapp.base.SchedulersCore
import com.mykuyauserapp.data.repo.LatLng
import com.mykuyauserapp.data.repo.LocationRepo
import org.junit.*

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
class MapPresenterTest {

    private val testScheduler = TestScheduler()
    @get:Rule
    val rxRule= RxRule(testScheduler)

    @RelaxedMockK
    lateinit var locationRepo: LocationRepo

    @RelaxedMockK
    lateinit var lifecycle: Lifecycle

    private val schedulers = SchedulersCore(testScheduler, testScheduler)
    fun createPresenter() = MapPresenterImpl(locationRepo, schedulers)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `when location updated and repo is success, then locationUpdated should called`() {
        every {
            locationRepo.updateLocation(any())
        } returns Completable.complete()
        val view= mockk<MapPresenterImpl.MapView>(relaxed = true)
        val presenter = createPresenter()
        presenter.attachView(view, lifecycle)
        presenter.submitLocation(LatLng(1.0, 1.0))
        testScheduler.advanceTimeBy(1000, TimeUnit.MILLISECONDS)
        verify {
            view.locationUpdated()
        }
    }

    @Test
    fun `when location updated and repo failed, then locationUpdatedError should called`() {
        every {
            locationRepo.updateLocation(any())
        } returns Completable.error(Exception("err"))

        val view= mockk<MapPresenterImpl.MapView>(relaxed = true)
        val presenter = createPresenter()
        val errorSlot = slot<Throwable>()
        presenter.attachView(view, lifecycle)
        presenter.submitLocation(LatLng(1.0, 1.0))
        testScheduler.advanceTimeBy(1000, TimeUnit.MILLISECONDS)
        verify {
            view.locationUpdateError(capture(errorSlot))
        }
        Assert.assertEquals("err", errorSlot.captured.message)
    }

}