package com.mykuyauserapp.map

import android.app.IntentService
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.ResultReceiver
import android.text.TextUtils
import android.util.Log
import androidx.annotation.Nullable
import java.io.IOException
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
class FetchAddressIntentService : IntentService("FetchAddressIs") {
    protected var mReceiver: ResultReceiver? = null
    override fun onHandleIntent(@Nullable intent: Intent?) {
        if (intent != null) {
            mReceiver = intent.getParcelableExtra(Constants.RECEIVER)
            val geocoder = Geocoder(this, Locale.getDefault())
            var errorMessage = ""
            var addresses: List<Address>? = null
            //getting location parceled from the intent extra
            val location = intent.getParcelableExtra<Location>(Constants.LOCATION_DATA_EXTRAS)

            // Check if receiver was properly registered.
            if (mReceiver == null) {
                Log.wtf("lol", "No receiver received. There is nowhere to send the results.")
                return
            }
            try {
                //third argument is 1 temporarly for now. single address will be returned
                addresses = geocoder.getFromLocation(
                    location!!.latitude,
                    location.longitude, 1
                )
            } catch (ioException: IOException) {
                //catching io problems
                errorMessage = "service not available"
                Log.e("ERROR_ADDRESS_GEOCODER", errorMessage, ioException)
            } catch (illegalArgException: IllegalArgumentException) {
                //invalid lat and long supplied
                errorMessage = "Coordinates not provided properly"
                Log.e("INVALID_COORDINATES", errorMessage, illegalArgException)
            }

            // Handle case where no address was found.
            if (addresses == null || addresses.isEmpty()) {
                if (errorMessage.isEmpty()) {
                    errorMessage = "Address not found"
                    Log.e("ADDRESS_NOT_FOUND", errorMessage)
                }
                deliverResultToReceiver(Constants.FAILURE_RESULT, errorMessage)
            } else {
                val address = addresses[0]
                val addressFragments = ArrayList<String?>()

                // Fetch the address lines using getAddressLine,
                // join them, and send them to the thread.
                for (i in 0..address.maxAddressLineIndex) {
                    addressFragments.add(address.getAddressLine(i))
                }
                Log.i(
                    "ADDRESS_FOUND",
                    TextUtils.join(System.getProperty("line.separator"), addressFragments)
                )
                deliverResultToReceiver(
                    Constants.RESULT_SUCESS,
                    TextUtils.join(
                        System.getProperty("line.separator"),
                        addressFragments
                    )
                )
            }
        }
    }

    private fun deliverResultToReceiver(resultCode: Int, message: String) {
        val bundle = Bundle()
        bundle.putString(Constants.RESULT_DATA_KEY, message)
        mReceiver!!.send(resultCode, bundle)
    }
}