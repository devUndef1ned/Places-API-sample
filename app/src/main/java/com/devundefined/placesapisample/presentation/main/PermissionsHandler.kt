package com.devundefined.placesapisample.presentation.main

interface PermissionsHandler {
    fun requestLocationPermission()
    fun hasLocationPermission(): Boolean
}