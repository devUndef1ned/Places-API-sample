package com.devundefined.placesapisample.infrastructure

import com.devundefined.placesapisample.domain.Location
import com.devundefined.placesapisample.domain.PlaceCategory
import com.devundefined.placesapisample.infrastructure.dto.*
import com.nhaarman.mockitokotlin2.*
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PlaceLoaderTests {

    @Mock
    lateinit var api: PlacesAPI

    private val placeLoader: PlaceLoader by lazy { PlaceLoaderImpl("apiKey", api) }

    @Test
    fun whenLoadByLocationAndType_shouldLoadThroughApiReturnResult_ifNextPageTokenIsNull() {
        runBlocking {
            val expectedLat = 1.1
            val expectedLng = 2.2
            val placeDto = createPlaceDto(expectedLat, expectedLng)
            val placeResultDto = PlacesResponseDto(listOf(placeDto), null, OK_STATUS)
            whenever(api.getPlacesByParms(any(), any(), any(), any())).thenReturn(placeResultDto)

            val result = placeLoader.loadByLocationAndType(USER_LOCATION, PlaceCategory.BAR)

            assertEquals(placeResultDto.places.size, result.size)
            assertEquals(Location(expectedLat, expectedLng), result.first().location)
            verify(api).getPlacesByParms(any(), any(), any(), any())
            verify(api, never()).getPlacesByToken(any(), any(), any())
        }
    }

    @Test
    fun whenLoadByLocationAndType_shouldLoadByToken_ifNextPageTokenIsNotNull() {
        runBlocking {
            val firstPortion = listOf(createPlaceDto(1.1, 2.2))
            val nextPageToken = "nextPageToken"
            val secondPortion = listOf(createPlaceDto(1.2, 3.4), createPlaceDto(4.4, 1.1))
            val firstResult = PlacesResponseDto(firstPortion, nextPageToken, OK_STATUS)
            val secondResult = PlacesResponseDto(secondPortion, null, OK_STATUS)
            whenever(api.getPlacesByParms(any(), any(), any(), any())).thenReturn(firstResult)
            whenever(api.getPlacesByToken(eq(nextPageToken), any(), any())).thenReturn(secondResult)

            val result = placeLoader.loadByLocationAndType(USER_LOCATION, PlaceCategory.BAR)

            assertEquals(firstPortion.size + secondPortion.size, result.size)
            verify(api).getPlacesByToken(eq(nextPageToken), any(), any())
        }
    }

    @Test
    fun whenLoadByLocationAndType_shouldLoadByTokenTwice_ifNextPageTokenIsNotNullAndFirstAttemptWasFailed() {
        runBlocking {
            val firstPortion = listOf(createPlaceDto(1.1, 2.2))
            val nextPageToken = "nextPageToken"
            val secondPortion = listOf(createPlaceDto(1.2, 3.4), createPlaceDto(4.4, 1.1))
            val firstResult = PlacesResponseDto(firstPortion, nextPageToken, OK_STATUS)
            val secondResult = PlacesResponseDto(secondPortion, null, OK_STATUS)
            whenever(api.getPlacesByParms(any(), any(), any(), any())).thenReturn(firstResult)
            whenever(api.getPlacesByToken(eq(nextPageToken), any(), eq(0))).thenThrow(IllegalStateException::class.java)
            whenever(api.getPlacesByToken(eq(nextPageToken), any(), eq(1))).thenReturn(secondResult)

            val result = placeLoader.loadByLocationAndType(USER_LOCATION, PlaceCategory.BAR)

            assertEquals(firstPortion.size + secondPortion.size, result.size)
            verify(api, times(2)).getPlacesByToken(eq(nextPageToken), any(), any())
        }
    }

    @Test
    fun whenLoadByLocationAndType_shouldLoadByTokenThreeTimesAndReturnFirstResult_ifNextPageTokenIsNotNullAndAllAttemptsWereFailed() {
        runBlocking {
            val firstPortion = listOf(createPlaceDto(1.1, 2.2), createPlaceDto(4.4, 1.1))
            val nextPageToken = "nextPageToken"
            val firstResult = PlacesResponseDto(firstPortion, nextPageToken, OK_STATUS)
            val failedResult = PlacesResponseDto(emptyList(), null, "INVALID_REQUEST")
            whenever(api.getPlacesByParms(any(), any(), any(), any())).thenReturn(firstResult)
            whenever(api.getPlacesByToken(eq(nextPageToken), any(), any())).thenReturn(failedResult)

            val result = placeLoader.loadByLocationAndType(USER_LOCATION, PlaceCategory.BAR)

            assertEquals(firstPortion.size, result.size)
            verify(api, times(3)).getPlacesByToken(eq(nextPageToken), any(), any())
        }
    }


    private fun createPlaceDto(lat: Double, lng: Double): PlaceDto {
        return PlaceDto(
            GeometryDto(LocationDto(lat, lng)),
            "place ${RND.nextInt()}",
            OpeningHoursDto(RND.nextBoolean()),
            "address ${RND.nextFloat()}",
            RND.nextFloat()
        )
    }

    companion object {
        private val RND = java.util.Random()
        private val USER_LOCATION = Location(1.0, 2.0)
        private const val OK_STATUS = "OK"
    }
}