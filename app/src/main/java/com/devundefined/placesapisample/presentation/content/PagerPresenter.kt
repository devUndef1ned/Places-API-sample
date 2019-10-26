package com.devundefined.placesapisample.presentation.content

import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class PagerPresenter(private val locationRequester: LocationRequester) : MvpPresenter<ContentView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        requestUserLocation()
    }

    fun requestUserLocation() {
        viewState.showLoading()
        // Async request of location
        locationRequester.getLastLocation({ userLocation ->
            viewState.showUserLocation(userLocation)
        }, { e ->
            viewState.showError(e)
        })
    }
}