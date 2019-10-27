package com.devundefined.placesapisample.di

import com.devundefined.placesapisample.domain.PlacesLoadService
import com.devundefined.placesapisample.domain.PlacesLoadServiceImpl
import com.devundefined.placesapisample.infrastructure.PlaceLoader
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {

    @Singleton
    @Provides
    fun providePlacesLoadService(placeLoader: PlaceLoader): PlacesLoadService = PlacesLoadServiceImpl(placeLoader)
}