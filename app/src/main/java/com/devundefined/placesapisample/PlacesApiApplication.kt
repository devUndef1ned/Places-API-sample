package com.devundefined.placesapisample

import android.app.Application
import com.devundefined.placesapisample.di.AppComponent
import com.devundefined.placesapisample.di.AppModule
import com.devundefined.placesapisample.di.DaggerAppComponent
import com.facebook.stetho.Stetho

class PlacesApiApplication : Application() {

    companion object {
        lateinit var INSTANCE: PlacesApiApplication
            private set
    }

    val appComponent: AppComponent by lazy { buildAppComponent() }

    private fun buildAppComponent(): AppComponent {
        return DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        appComponent
        Stetho.initializeWithDefaults(this)
    }
}