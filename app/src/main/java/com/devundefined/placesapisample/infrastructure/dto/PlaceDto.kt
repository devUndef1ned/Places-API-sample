package com.devundefined.placesapisample.infrastructure.dto

import com.google.gson.annotations.SerializedName

data class PlaceDto(
    @SerializedName("geometry") val geometry: GeometryDto,
    @SerializedName("name") val name: String,
    @SerializedName("opening_hours") val openingHours: OpeningHoursDto?,
    @SerializedName("vicinity") val address: String,
    @SerializedName("rating") val rating: Float?
)

data class GeometryDto(@SerializedName("location") val location: LocationDto)
data class LocationDto(@SerializedName("lat") val latitude: Double, @SerializedName("lng") val longitude: Double)
data class OpeningHoursDto(@SerializedName("open_now") val openNow: Boolean)