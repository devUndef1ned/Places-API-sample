package com.devundefined.placesapisample.di

import android.content.Context
import com.devundefined.placesapisample.presentation.content.AndroidLocationRequester
import com.devundefined.placesapisample.presentation.content.LocationRequester
import com.devundefined.placesapisample.presentation.content.PagerPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContentModule {

    @Provides
    @Singleton
    fun provideLocationRequester(context: Context): LocationRequester = AndroidLocationRequester(context)

    @Provides
    @Singleton
    fun providePresenter(locationRequester: LocationRequester): PagerPresenter = PagerPresenter(locationRequester)
}