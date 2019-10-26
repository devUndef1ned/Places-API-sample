package com.devundefined.placesapisample.presentation.pager

import com.devundefined.placesapisample.domain.UserLocation
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface PagerView : MvpView {
    fun showLoading()
    fun showUserLocationFragment(userLocation: UserLocation)
    fun showError(e: Throwable)
}