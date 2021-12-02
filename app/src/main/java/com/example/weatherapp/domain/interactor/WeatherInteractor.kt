package com.example.weatherapp.domain.interactor

import com.example.weatherapp.domain.model.CoordModel
import com.example.weatherapp.domain.model.ForecastWeekModel
import com.example.weatherapp.domain.model.ForecastTodayModel
import com.example.weatherapp.domain.repository.WeatherRepository
import io.reactivex.rxjava3.core.Single

class WeatherInteractor(private val repository: WeatherRepository) {

    fun getCoord(cityName: String): Single<CoordModel> {
        return repository.saveCoord(cityName)
    }

    fun getForecastToday(
        cityName: String
    ): Single<ForecastTodayModel> {
        return repository.getForecastToday(cityName)
    }

    fun getForecastTodayCurrentLocation(
        lat: String,
        lon: String
    ): Single<ForecastTodayModel> {
        return repository.getForecastTodayCurrentLocation(lat, lon)
    }

    fun getForecastWeek(
    ): Single<List<ForecastWeekModel>> {
        return repository.getForecastWeek()
    }

    fun getForecastWeekCurrentLocation(
        lat: String,
        lon: String
    ): Single<List<ForecastWeekModel>> {
        return repository.getForecastWeekCurrentLocation(lat, lon)
    }
}