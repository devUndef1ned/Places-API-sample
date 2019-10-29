package com.devundefined.placesapisample.domain

import com.devundefined.placesapisample.infrastructure.PlaceLoader

interface PlacesLoadService {
    fun loadByLocationAndCategory(userLocation: Location, placeCategory: PlaceCategory): List<Place>
}

class PlacesLoadServiceImpl(private val placeLoader: PlaceLoader) : PlacesLoadService {
    override fun loadByLocationAndCategory(
        userLocation: Location,
        placeCategory: PlaceCategory
    ): List<Place> {
        return placeLoader.loadByLocationAndType(userLocation, placeCategory)
            .sortedBy { place -> place.distance }
    }
}