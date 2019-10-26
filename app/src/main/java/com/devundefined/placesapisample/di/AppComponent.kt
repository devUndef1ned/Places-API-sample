package com.devundefined.placesapisample.di

import com.devundefined.placesapisample.presentation.pager.PagerPresenter
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, PagerModule::class])
@Singleton
interface AppComponent {

    fun pagerPresenter(): PagerPresenter
}