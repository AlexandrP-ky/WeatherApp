package com.example.weatherapp.di

import com.example.weatherapp.domain.interactor.WeatherInteractor
import org.koin.dsl.module


val domainModule = module {

    factory {
        WeatherInteractor(repository = get())
    }
}