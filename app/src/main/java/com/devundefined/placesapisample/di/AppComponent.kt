package com.devundefined.placesapisample.di

import com.devundefined.placesapisample.domain.PlacesLoadService
import com.devundefined.placesapisample.presentation.content.PagerPresenter
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, ContentModule::class, DomainModule::class, InfrastructureModule::class])
@Singleton
interface AppComponent {

    fun pagerPresenter(): PagerPresenter

    fun placesLoadService(): PlacesLoadService
}