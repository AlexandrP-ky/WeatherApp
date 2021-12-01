package com.example.weatherapp.domain.interactor

import com.example.weatherapp.domain.model.CoordModel
import com.example.weatherapp.domain.model.ForecastWeekModel
import com.example.weatherapp.domain.model.ForecastTodayModel
import com.example.weatherapp.domain.repository.WeatherRepository
import io.reactivex.rxjava3.core.Single

class WeatherInteractor(private val repository: WeatherRepository) {

    fun getCoord(cityName: String, apiKey: String): Single<CoordModel> {
        return repository.saveCoord(cityName, apiKey)
    }

    fun getForecastToday(
        cityName: String,
        units: String,
        apiKey: String,
        lang: String
    ): Single<ForecastTodayModel> {
        return repository.getForecastToday(cityName, units, apiKey, lang)
    }

    fun getForecastTodayCurrentLocation(
        lat: String,
        lon: String,
        exclude: String,
        units: String,
        apiKey: String
    ): Single<ForecastTodayModel> {
        return repository.getForecastTodayCurrentLocation(lat, lon, exclude, units, apiKey)
    }

    fun getForecastWeek(
        exclude: String,
        units: String,
        apiKey: String
    ): Single<List<ForecastWeekModel>> {
        return repository.getForecastWeek(exclude, units, apiKey)
    }

    fun getForecastWeekCurrentLocation(
        lat: String,
        lon: String,
        exclude: String,
        units: String,
        apiKey: String
    ): Single<List<ForecastWeekModel>> {
        return repository.getForecastWeekCurrentLocation(lat, lon, exclude, units, apiKey)
    }
}