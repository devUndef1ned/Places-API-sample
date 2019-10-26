package com.devundefined.placesapisample.presentation.content

import com.devundefined.placesapisample.domain.Location
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface ContentView : MvpView {
    fun showLoading()
    fun showUserLocation(userLocation: Location)
    fun showError(e: Throwable)
}