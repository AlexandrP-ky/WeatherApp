package com.example.weatherapp.domain.repository

import com.example.weatherapp.domain.model.CoordModel
import com.example.weatherapp.domain.model.ForecastWeekModel
import com.example.weatherapp.domain.model.ForecastTodayModel
import io.reactivex.rxjava3.core.Single

interface WeatherRepository {
    fun saveCoord(cityName: String, apiKey: String): Single<CoordModel>

    fun getForecastToday(
        cityName: String,
        units: String,
        lang: String,
        apiKey: String
    ): Single<ForecastTodayModel>

    fun getForecastWeek(
        exclude: String,
        units: String,
        apiKey: String
    ): Single<List<ForecastWeekModel>>

    fun getForecastWeekCurrentLocation(
        lat: String,
        lon: String,
        exclude: String,
        units: String,
        apiKey: String
    ): Single<List<ForecastWeekModel>>

    fun getForecastTodayCurrentLocation(
        lat: String,
        lon: String,
        exclude: String,
        units: String,
        apiKey: String
    ): Single<ForecastTodayModel>
}