package com.devundefined.placesapisample.infrastructure

import com.devundefined.placesapisample.infrastructure.dto.PlacesResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface PlacesAPI {
    @GET("maps/api/place/nearbysearch/json")
    suspend fun getPlacesByParms(
        @Query("key") apiKey: String,
        @Query("location") location: String,
        @Query("radius") radius: Int,
        @Query("type") type: String
    ): PlacesResponseDto

    @GET("maps/api/place/nearbysearch/json")
    suspend fun getPlacesByToken(@Query("pagetoken") pageToken: String, @Query("key") apiKey: String): PlacesResponseDto
}