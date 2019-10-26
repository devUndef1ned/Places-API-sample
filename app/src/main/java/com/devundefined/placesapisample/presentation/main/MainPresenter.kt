package com.devundefined.placesapisample.presentation.main

import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {

    fun setPermissionHandler(permissionHandler: PermissionsHandler) {
        checkLocationPermission(permissionHandler)
    }

    fun handlePermissionsResult(isPermissionGranted: Boolean) {
        if (isPermissionGranted) {
            viewState.showContent()
        } else {
            viewState.showPermissionError()
        }
    }

    private fun checkLocationPermission(permissionHandler: PermissionsHandler) {
        if (!permissionHandler.hasLocationPermission()) {
            permissionHandler.requestLocationPermission()
        } else {
            viewState.showContent()
        }
    }
}