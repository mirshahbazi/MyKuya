package com.mykuyauserapp.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.ResultReceiver
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng

import kotlinx.android.synthetic.main.app_action_bar.*
import kotlinx.android.synthetic.main.fragment_map.*
import org.koin.android.ext.android.inject
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.mykuyauserapp.base.navigation.LocationDTO


/**
 * *
 * *          ____  ____ _____ ___   ____
 * *         | \ \ / / |/ _  || \ \ / / |
 * *         | |\ V /| | (_| || |\ V /| |
 * *         |_| \_/ |_|\__,_||_| \_/ |_|
 * *
 * Created by Mohammad Ali Mirshahbazi(MAM)
 */
class MapFragment : Fragment(), MapPresenterImpl.MapView {
    private var map: GoogleMap? = null
    private val args: MapFragmentArgs by navArgs()
    private lateinit var resultReceiver :addressResultReceiver
    private val mapPresenter: MapPresenterImpl by inject()
    private lateinit var locationCallback: LocationCallback

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(
        R.layout.fragment_map, container, false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapPresenter.attachView(this, viewLifecycleOwner.lifecycle)
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 123)
        resultReceiver = addressResultReceiver( Handler())
        imageMapPin.post {
            imageMapPin.also {
                it.translationY -= it.height / 2
            }
        }

        buttonMapSubmit.setOnClickListener {
            map?.cameraPosition?.target?.let {
                mapPresenter.submitLocation(
                    com.mykuyauserapp.data.repo.LatLng(it.latitude, it.longitude)
                )
            }
        }
        btnBack.setOnClickListener {
            locationUpdated()
        }
    }

    fun setAddress(address: String){
        tvTitle.text = address
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 123) {
            val fineLocationIndex =
                permissions.indexOfFirst { it == Manifest.permission.ACCESS_FINE_LOCATION }
            loadMap(grantResults[fineLocationIndex] == PermissionChecker.PERMISSION_GRANTED)
        }
    }

    @SuppressLint("MissingPermission")
    private fun loadMap(showMyLocation: Boolean) {
        (childFragmentManager.findFragmentById(R.id.fragmentMap) as SupportMapFragment).getMapAsync {
            map = it
            it.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    args.location.toLatLng(), 13f
                )
            )
            it.uiSettings.apply {
                if (showMyLocation) {
                    this.isMyLocationButtonEnabled = true
                }
            }
            if (showMyLocation) {
                it.isMyLocationEnabled = true
            }
        }

    }

    override fun locationUpdated() {
        findNavController().popBackStack()
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun locationUpdateError(it: Throwable?) {
        Toast.makeText(context!!, getString(R.string.error_happened), Toast.LENGTH_LONG).show()
    }

    private fun LocationDTO.toLatLng() = LatLng(latitude, longitude)




    @SuppressLint("MissingPermission")
    private fun getLocation() {
        val locationRequest = LocationRequest()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 3000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        LocationServices.getFusedLocationProviderClient(requireActivity()).requestLocationUpdates(locationRequest,locationCallback, Looper.getMainLooper())
    }

    fun fetchAddressFromLatLong(location: Location){
        val intent = Intent(requireActivity(), FetchAddressIntentService::class.java)
        intent.putExtra(Constants.RECEIVER, resultReceiver)
        intent.putExtra(Constants.LOCATION_DATA_EXTRAS, location)
        requireActivity().startService(intent)
    }

    class addressResultReceiver(handler: Handler) : ResultReceiver(handler) {
        override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
            super.onReceiveResult(resultCode, resultData)
            if (resultCode == Constants.RESULT_SUCESS){
                val address = resultData?.getString(Constants.RESULT_DATA_KEY)
                Log.i("address:", address.toString())
                // TODO: 10/7/20 update the view

            }else {
                Log.w("getLocation:","error on receive address")
            }
        }
    }
}