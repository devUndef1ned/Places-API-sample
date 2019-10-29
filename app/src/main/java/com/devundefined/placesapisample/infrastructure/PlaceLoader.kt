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
                var portion = placesAPI.getPlacesByParms(apiKey, locationQueryString, RADIUS_METERS, category.value)
                data.addAll(portion.places)
                var token = portion.nextPageToken
                while (token != null) {
                    portion = try {
                        loadByToken(token)
                    } catch (e: Exception) {
                        break
                    }
                    data.addAll(portion.places)
                    token = portion.nextPageToken
                }
                data.map { dto -> dto.toModel(userLocation, category) }
            }
        }
    }

    private suspend fun loadByToken(token: String, attempt: Int = 0): PlacesResponseDto {
        require (attempt < MAX_ATTEMPT_COUNT)
        return try {
            val candidate = placesAPI.getPlacesByToken(token, apiKey, attempt)
            if (candidate.isSuccess()) {
                return candidate
            } else {
                throw IllegalStateException("Not success request")
            }
        } catch (e: Exception) {
            delay(RETRY_SLEEP_TIME)
            loadByToken(token, attempt + 1)
        }
    }

    companion object {
        private const val RADIUS_METERS = 5000
        private const val MAX_ATTEMPT_COUNT = 3
        private const val RETRY_SLEEP_TIME = 1000L
    }
}