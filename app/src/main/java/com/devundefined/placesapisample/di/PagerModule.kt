package com.devundefined.placesapisample.di

import android.content.Context
import com.devundefined.placesapisample.presentation.pager.AndroidLocationRequester
import com.devundefined.placesapisample.presentation.pager.LocationRequester
import com.devundefined.placesapisample.presentation.pager.PagerPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PagerModule {

    @Provides
    @Singleton
    fun provideLocationRequester(context: Context): LocationRequester = AndroidLocationRequester(context)

    @Provides
    @Singleton
    fun providePresenter(locationRequester: LocationRequester): PagerPresenter = PagerPresenter(locationRequester)
}