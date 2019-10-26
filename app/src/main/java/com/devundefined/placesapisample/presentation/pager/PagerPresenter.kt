package com.devundefined.placesapisample.presentation.pager

import com.devundefined.placesapisample.domain.UserLocation
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class PagerPresenter : MvpPresenter<PagerView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        requestUserLocation()
    }

    private fun requestUserLocation() {
        viewState.showLoading()
        // Async request of location
        val location = UserLocation(0.0, 0.0)
        viewState.showUserLocationFragment(location)
    }
}