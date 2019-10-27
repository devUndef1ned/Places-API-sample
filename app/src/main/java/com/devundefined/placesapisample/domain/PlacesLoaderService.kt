package com.devundefined.placesapisample.domain

import com.devundefined.placesapisample.infrastructure.PlaceLoader

interface PlacesLoadService {
    fun loadByLocation(userLocation: Location): List<Place>
}

class PlacesLoadServiceImpl(private val placeLoader: PlaceLoader) : PlacesLoadService {
    override fun loadByLocation(userLocation: Location): List<Place> {
        return PlaceCategory.values().fold(listOf<Place>()) { acc, placeCategory ->
            acc + placeLoader.loadByLocationAndType(userLocation, placeCategory)
        }
            .sortedBy { place -> place.distance }
    }
}