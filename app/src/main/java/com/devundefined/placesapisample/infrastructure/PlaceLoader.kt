package com.devundefined.placesapisample.infrastructure

import com.devundefined.placesapisample.domain.Location
import com.devundefined.placesapisample.domain.OpenNow
import com.devundefined.placesapisample.domain.Place
import com.devundefined.placesapisample.domain.PlaceCategory
import com.devundefined.placesapisample.infrastructure.dto.PlaceDto
import com.devundefined.placesapisample.infrastructure.dto.PlacesResponseDto
import kotlinx.coroutines.*
import java.lang.Exception

interface PlaceLoader {
    fun loadByLocationAndType(userLocation: Location, category: PlaceCategory): List<Place>
}

class PlaceLoaderImpl(
    private val apiKey: String,
    private val placesAPI: PlacesAPI
) : PlaceLoader {

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private fun PlaceDto.toModel(userLocation: Location, category: PlaceCategory): Place {
        val placeLocation = Location(geometry.location.latitude, geometry.location.longitude)
        return Place(
            name,
            category,
            rating ?: 0.0f,
            address,
            placeLocation,
            placeLocation.getDistance(userLocation),
            openingHours?.openNow?.let { isOpen -> if (isOpen) OpenNow.OPEN else OpenNow.CLOSED }
                ?: OpenNow.UNKNOWN
        )
    }

    override fun loadByLocationAndType(
        userLocation: Location,
        category: PlaceCategory
    ): List<Place> {
        return runBlocking {
            withContext(scope.coroutineContext) {
                val data = mutableListOf<PlaceDto>()
                val locationQueryString = "${userLocation.latitude},${userLocation.longitude}"
                val radius = 5000
                var portion =
                    placesAPI.getPlacesByParms(apiKey, locationQueryString, radius, category.value)
                var token = portion.nextPageToken
                while (token != null) {
                    data.addAll(portion.places)
                    portion = loadByToken(token)
                    token = portion.nextPageToken
                }
                data.map { dto -> dto.toModel(userLocation, category) }
            }
        }
    }

    private suspend fun loadByToken(token: String, attempt: Int = 0): PlacesResponseDto {
        require(attempt < MAX_ATTEMPT_COUNT)
        return try {
            placesAPI.getPlacesByToken(token, apiKey)
        } catch (e: Exception) {
            delay(RETRY_SLEEP_TIME)
            loadByToken(token, attempt + 1)
        }
    }

    companion object {
        private const val MAX_ATTEMPT_COUNT = 3
        private const val RETRY_SLEEP_TIME = 200L
    }
}