package com.devundefined.placesapisample.presentation.placelist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devundefined.placesapisample.R
import com.devundefined.placesapisample.domain.Place

class PlacesAdapter(private val places: List<Place>) : RecyclerView.Adapter<PlacesVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesVH {
        return PlacesVH(LayoutInflater.from(parent.context).inflate(R.layout.item_place, parent, false))
    }

    override fun getItemCount() = places.size

    override fun onBindViewHolder(holder: PlacesVH, position: Int) {
        PlacesBinder.bindData(holder, places[position])
    }
}