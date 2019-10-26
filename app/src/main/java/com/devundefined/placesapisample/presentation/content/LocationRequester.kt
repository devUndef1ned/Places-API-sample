package com.devundefined.placesapisample.presentation.content

import android.content.Context
import com.devundefined.placesapisample.domain.Location
import com.google.android.gms.location.LocationServices

interface LocationRequester {
    fun getLastLocation(consumer: (Location) -> Unit, errorHandler: (Throwable) -> Unit)
}

class AndroidLocationRequester(private val context: Context) : LocationRequester {
    override fun getLastLocation(consumer: (Location) -> Unit, errorHandler: (Throwable) -> Unit) {
        LocationServices.getFusedLocationProviderClient(context).lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                consumer(Location(location.latitude, location.longitude))
            } else {
                errorHandler(IllegalStateException("Location is null!!!"))
            }
        }
            .addOnFailureListener(errorHandler)
    }
}