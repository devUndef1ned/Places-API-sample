package com.devundefined.placesapisample.domain

data class Place(
    val name: String,
    val category: PlaceCategory,
    val rating: Float,
    val address: String,
    val location: Location,
    val distance: Double,
    val isOpenNow: OpenNow
)

enum class OpenNow {
    OPEN, CLOSED, UNKNOWN
}