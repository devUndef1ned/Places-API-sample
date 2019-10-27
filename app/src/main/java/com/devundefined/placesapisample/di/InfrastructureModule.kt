package com.devundefined.placesapisample.di

import com.devundefined.placesapisample.BuildConfig
import com.devundefined.placesapisample.infrastructure.PlaceLoader
import com.devundefined.placesapisample.infrastructure.PlaceLoaderImpl
import com.devundefined.placesapisample.infrastructure.PlacesAPI
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient


@Module
class InfrastructureModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl("https://maps.googleapis.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providePlacesAPI(retrofit: Retrofit) = retrofit.create(PlacesAPI::class.java)

    @Provides
    @Singleton
    fun providePlaceLoader(placesAPI: PlacesAPI): PlaceLoader = PlaceLoaderImpl(BuildConfig.ApiSecretKey, placesAPI)
}