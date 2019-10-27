package com.devundefined.placesapisample.presentation.placelist

import com.devundefined.placesapisample.domain.Place
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface PlaceListView : MvpView {
    fun showLoading()
    fun showError(e: Throwable)
    fun showPlaceList(placeList: List<Place>)
}