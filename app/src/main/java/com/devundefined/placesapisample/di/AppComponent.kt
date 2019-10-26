package com.devundefined.placesapisample.di

import com.devundefined.placesapisample.presentation.content.PagerPresenter
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, ContentModule::class])
@Singleton
interface AppComponent {

    fun pagerPresenter(): PagerPresenter
}