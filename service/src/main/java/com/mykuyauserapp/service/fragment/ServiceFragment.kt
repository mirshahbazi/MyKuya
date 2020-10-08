package com.mykuyauserapp.service.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mykuyauserapp.base.Loadable
import com.mykuyauserapp.base.navigation.LocationDTO
import com.mykuyauserapp.base.onFail
import com.mykuyauserapp.base.onLoad
import com.mykuyauserapp.base.utils.stringHelper.*
import com.mykuyauserapp.data.repo.LatLng
import com.mykuyauserapp.data.repo.News
import com.mykuyauserapp.data.repo.Service
import com.mykuyauserapp.data.repo.User
import com.mykuyauserapp.service.R

import com.mykuyauserapp.service.contract.ServicePresenterImpl
import com.mykuyauserapp.service.adapter.NewsAdapter
import com.mykuyauserapp.service.adapter.ServiceAdapter
import kotlinx.android.synthetic.main.fragment_service.*
import org.koin.android.ext.android.inject
import java.util.*

/**
 * *
 * *          ____  ____ _____ ___   ____
 * *         | \ \ / / |/ _  || \ \ / / |
 * *         | |\ V /| | (_| || |\ V /| |
 * *         |_| \_/ |_|\__,_||_| \_/ |_|
 * *
 * Created by Mohammad Ali Mirshahbazi(MAM)
 */
class ServiceFragment : Fragment(), ServicePresenterImpl.ServiceView {

    private val presenter: ServicePresenterImpl by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(
        R.layout.fragment_service, container, false
    )

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this, viewLifecycleOwner.lifecycle)
        val featuredAdapter = ServiceAdapter()
        val allServicesAdapter = ServiceAdapter().apply {
            isExpanded = false
            updateImage(isExpanded)
        }
        imageServiceExpanded.setOnClickListener {
            recyclerServiceAll.adapter().apply {
                isExpanded = !isExpanded
                notifyDataSetChanged()
                updateImage(isExpanded)
            }
        }
        recyclerServiceAll.adapter = allServicesAdapter
        recyclerServiceFeatureCardFeatures.adapter = featuredAdapter
        recyclerServiceNews.adapter = NewsAdapter()
        textServiceLocation.setCompoundDrawablesWithIntrinsicBounds(
            ContextCompat.getDrawable(context!!, R.drawable.ic_location_pin_with_hole_and_shadow),
            null, null, null
        )

        textServiceLocation.setOnClickListener {
            presenter.requestMap()
        }
    }

    private fun updateImage(expanded: Boolean) {
        imageServiceExpanded.rotationX = if (expanded) 0f else 180f
    }

    override fun promotedServices(data: Loadable<List<Service>>) {
        data.onLoad {
            recyclerServiceFeatureCardFeatures.adapter().apply {
                items = this@onLoad
                notifyDataSetChanged()
            }
        }

        data.onFail {

        }
    }

    override fun allServices(data: Loadable<List<Service>>) {
        data.onLoad {
            recyclerServiceAll.adapter().apply {
                items = this@onLoad
                notifyDataSetChanged()
            }
        }
        data.onFail {

        }
    }

    override fun updateNews(data: Loadable<List<News>>) {
        data.onLoad {
            (recyclerServiceNews.adapter as NewsAdapter).apply {
                items = this@onLoad
                notifyDataSetChanged()
            }
        }
        data.onFail {

        }
    }

    override fun updateProfile(data: Loadable<User>) {
        data.onLoad {
            val spanned = spannable {
                color(
                    Color.parseColor("#555555"),
                    normal(getGreetingMessage() + " ")
                ) +  color(Color.parseColor("#555555"),
                bold(name + "!")
                )
            }
            /**
             * load data from mock data
             * and show the profile in the
             * main view
             * */
            textServiceWelcome.text = spanned
        }
        data.onFail {

        }
    }

    override fun updateCity(name: String) {
        textServiceLocation.text = name
    }

    override fun loadMap(location: LatLng) {
        findNavController().navigate(
            ServiceFragmentDirections.actionServiceToMap(
                location.toLocationNto()
            )
        )
    }

    private fun getGreetingMessage():String{
        val c = Calendar.getInstance()
        val timeOfDay = c.get(Calendar.HOUR_OF_DAY)

        return when (timeOfDay) {
            in 0..11 -> "Good Morning,"
            in 12..15 -> "Good Afternoon,"
            in 16..20 -> "Good Evening,"
            in 21..23 -> "Good Night,"
            else -> "Welcome,"
        }
    }

    private fun RecyclerView.adapter() = (adapter as ServiceAdapter)
}


fun LatLng.toLocationNto() = LocationDTO(latitude, longitude)
