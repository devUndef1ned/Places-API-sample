package com.devundefined.placesapisample.domain

import kotlin.math.pow
import kotlin.math.sqrt

data class Place(
    val name: String,
    val category: PlaceCategory,
    val rating: Int,
    val address: String,
    val location: Location
) {
    fun getDistance(from: Location): Double {
        return sqrt(
            (from.latitude - location.latitude).pow(2) + (from.longitude - location.longitude).pow(
                2
            )
        )
    }
}