package com.devundefined.placesapisample.infrastructure.dto

import com.google.gson.annotations.SerializedName

data class PlacesResponseDto(
    @SerializedName("results") val places: List<PlaceDto>,
    @SerializedName("next_page_token") val nextPageToken: String?,
    @SerializedName("status") val status: String
) {
    fun isSuccess(): Boolean {
        return status == OK_STATUS
    }

    companion object {
        private const val OK_STATUS = "OK"
    }
}