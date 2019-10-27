package com.devundefined.placesapisample.domain

import java.io.Serializable
import kotlin.math.pow
import kotlin.math.sqrt

data class Location(val latitude: Double, val longitude: Double): Serializable {
    fun getDistance(from: Location): Double {
        return sqrt(
            (from.latitude - latitude).pow(2) + (from.longitude - longitude).pow(
                2
            )
        )
    }
}