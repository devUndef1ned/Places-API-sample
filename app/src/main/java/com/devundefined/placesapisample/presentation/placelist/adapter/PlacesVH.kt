package com.devundefined.placesapisample.presentation.placelist.adapter

import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.devundefined.placesapisample.R
import com.devundefined.placesapisample.switchVisibility

class PlacesVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val title: TextView
        get() = itemView.findViewById(R.id.title)
    private val openNow: TextView
        get() = itemView.findViewById(R.id.openNow)
    private val address: TextView
        get() = itemView.findViewById(R.id.address)
    private val distance: TextView
        get() = itemView.findViewById(R.id.distance)
    private val rating: RatingBar
        get() = itemView.findViewById(R.id.rating)

    fun setTitle(titleText: String) {
        title.text = titleText
    }
    fun showOpenNow(shouldBeShown: Boolean) {
        openNow.switchVisibility(shouldBeShown)
    }
    fun setOpenNowText(text: String) {
        openNow.text = text
    }
    fun setOpenNowColor(colorResId: Int) {
        openNow.setTextColor(ContextCompat.getColor(itemView.context, colorResId))
    }
    fun setAddress(addressText: String) {
        address.text = addressText
    }
    fun setDistance(distanceText: String) {
        distance.text = distanceText
    }
    fun setRating(rating: Float) {
        this.rating.rating = rating
    }
}