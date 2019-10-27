package com.devundefined.placesapisample.presentation.placelist

import com.devundefined.placesapisample.domain.Location
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class PlaceListPresenter(private val userLocation: Location) : MvpPresenter<PlaceListView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        requestPlacesByUserLocation(userLocation)
    }

    fun requestPlaces() {
        requestPlacesByUserLocation(userLocation)
    }

    private fun requestPlacesByUserLocation(userLocation: Location) {
        viewState.showLoading()
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}