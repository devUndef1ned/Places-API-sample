package com.devundefined.placesapisample.domain

import java.io.Serializable
import kotlin.math.*

data class Location(val latitude: Double, val longitude: Double): Serializable {
    // Solution from https://stackoverflow.com/questions/3694380/calculating-distance-between-two-points-using-latitude-longitude
    fun getDistance(from: Location): Double {
        val R = EARTH_RADIUS_KM
        val dLat = Math.toRadians(latitude - from.latitude)
        val dLong = Math.toRadians(longitude - from.longitude)
        val a = sin(dLat/2).pow(2) + cos(Math.toRadians(latitude)) * cos(Math.toRadians(from.latitude)) *
                sin(dLong/2).pow(2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return R * c
    }

    companion object {
        private const val EARTH_RADIUS_KM = 6371
    }
}