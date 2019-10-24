package com.devundefined.placesapisample.main

interface PermissionsHandler {
    fun requestLocationPermission()
    fun hasLocationPermission(): Boolean
}