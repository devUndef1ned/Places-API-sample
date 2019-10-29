package com.devundefined.placesapisample.presentation.placelist.adapter

import com.devundefined.placesapisample.R
import com.devundefined.placesapisample.domain.OpenNow
import com.devundefined.placesapisample.domain.Place

object PlacesBinder {
    fun bindData(placesVH: PlacesVH, place: Place) {
        placesVH.setTitle(place.name)
        placesVH.setAddress(place.address)
        placesVH.setRating(place.rating)
        placesVH.showOpenNow(place.isOpenNow != OpenNow.UNKNOWN)
        placesVH.setOpenNowColor(if (place.isOpenNow == OpenNow.OPEN) R.color.colorGreen else R.color.colorRed)
        placesVH.setOpenNowText(place.isOpenNow.name)
        placesVH.setDistance(getDistanceString(placesVH, place))
    }

    private fun getDistanceString(placesVH: PlacesVH, place: Place): String {
        return if (place.distance >= 1.0) placesVH.itemView.context.getString(
            R.string.distance_km,
            place.distance
        ) else placesVH.itemView.context.getString(
            R.string.distance_m,
            (place.distance * 1000).toInt()
        )
    }
}