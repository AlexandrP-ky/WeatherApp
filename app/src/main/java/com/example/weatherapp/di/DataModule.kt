package com.example.weatherapp.di

import com.example.weatherapp.core.RetrofitInstance
import com.example.weatherapp.data.api.WeatherService
import com.example.weatherapp.data.repository.WeatherRepositoryImpl
import com.example.weatherapp.data.storage.CoordStorage
import com.example.weatherapp.data.storage.SharedPrefCoordStorage
import com.example.weatherapp.domain.repository.WeatherRepository
import org.koin.dsl.module


val dataModule = module {

    single<WeatherRepository> {
        WeatherRepositoryImpl(
            coordStorage = get(),
            retrofitInstance = get()
        )
    }
    single<CoordStorage> {
        SharedPrefCoordStorage(context = get())
    }
    single<WeatherService> {
        RetrofitInstance.getRetrofitInstance().create(WeatherService::class.java)
    }
}