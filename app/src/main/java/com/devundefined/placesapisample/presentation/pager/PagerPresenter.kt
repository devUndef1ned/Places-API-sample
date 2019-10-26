package com.devundefined.placesapisample.presentation.pager

import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class PagerPresenter(private val locationRequester: LocationRequester) : MvpPresenter<PagerView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        requestUserLocation()
    }

    fun requestUserLocation() {
        viewState.showLoading()
        // Async request of location
        locationRequester.getLastLocation({ userLocation ->
            viewState.showUserLocationFragment(userLocation)
        }, { e ->
            viewState.showError(e)
        })
    }
}