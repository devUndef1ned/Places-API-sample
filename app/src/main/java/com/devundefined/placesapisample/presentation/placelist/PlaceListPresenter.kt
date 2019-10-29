package com.devundefined.placesapisample.presentation.placelist

import com.devundefined.placesapisample.domain.Location
import com.devundefined.placesapisample.domain.PlaceCategory
import com.devundefined.placesapisample.domain.PlacesLoadService
import kotlinx.coroutines.*
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class PlaceListPresenter(
    private val userLocation: Location,
    private val placeCategory: PlaceCategory,
    private val placesLoadService: PlacesLoadService
) : MvpPresenter<PlaceListView>() {

    private val bgScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val uiScope = CoroutineScope(Dispatchers.Main)

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        requestPlacesByUserLocation(userLocation)
    }

    fun requestPlaces() {
        requestPlacesByUserLocation(userLocation)
    }

    private fun requestPlacesByUserLocation(userLocation: Location) {
        viewState.showLoading()
        runBlocking {
            bgScope.launch {
                try {
                    val places =
                        placesLoadService.loadByLocationAndCategory(userLocation, placeCategory)
                    uiScope.launch {
                        viewState.showPlaceList(places)
                    }
                } catch (e: Exception) {
                    uiScope.launch {
                        viewState.showError(e)
                    }
                }
            }
        }
    }
}