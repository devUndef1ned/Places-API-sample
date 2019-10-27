package com.devundefined.placesapisample.presentation.placelist

import com.devundefined.placesapisample.domain.Location
import com.devundefined.placesapisample.domain.PlaceCategory

data class PlaceViewObject(
    val name: String,
    val category: PlaceCategory,
    val rating: Int,
    val address: String,
    val location: Location,
    val distance: Double
)