package com.mykuyauserapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.mykuyauserapp.base.navigation.LocationDTO
import com.mykuyauserapp.data.repo.LatLng
import com.mykuyauserapp.mykuya.R

import com.mykuyauserapp.service.fragment.toLocationNto
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.inject
/**
 * *
 * *          ____  ____ _____ ___   ____
 * *         | \ \ / / |/ _  || \ \ / / |
 * *         | |\ V /| | (_| || |\ V /| |
 * *         |_| \_/ |_|\__,_||_| \_/ |_|
 * *
 * Created by Mohammad Ali Mirshahbazi(MAM)
 */
class MainFragment : Fragment(), MainPresenterImpl.MainView {
    private val mainPresenter: MainPresenterImpl by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(
        R.layout.fragment_main, container, false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainPresenter.attachView(this, viewLifecycleOwner.lifecycle)
        val navigationHost = childFragmentManager.fragments[0] as NavHostFragment
        val controller = navigationHost.navController
        bottomMainNavigation.setOnNavigationItemSelectedListener {
            /**
             * locate the fragment in bottom navigation view
             * when user select action
             * */
            when(it.itemId) {
                R.id.home -> true.also {
                    controller.setGraph(R.navigation.service_nav_graph)
                }
                else -> false
            }
        }

        bottomMainNavigation.selectedItemId = R.id.home
    }

    override fun loadMapScreen() {
        findNavController().navigate(MainFragmentDirections.actionMainToMap(defaultLocation))
    }

    override fun loadMap(location: LatLng) {
        findNavController().navigate(MainFragmentDirections.actionMainToMap(location.toLocationNto()))
    }

    /**
     * set the default lat and lon for map
     * */
    companion object {
        val defaultLocation = LocationDTO(35.815283, 50.986490)
    }
}