package com.example.weatherapp.domain.repository

import com.example.weatherapp.domain.model.CoordModel
import com.example.weatherapp.domain.model.ForecastWeekModel
import com.example.weatherapp.domain.model.ForecastTodayModel
import io.reactivex.rxjava3.core.Single

interface WeatherRepository {
    fun saveCoord(cityName: String): Single<CoordModel>

    fun getForecastToday(
        cityName: String
    ): Single<ForecastTodayModel>

    fun getForecastWeek(

    ): Single<List<ForecastWeekModel>>

    fun getForecastWeekCurrentLocation(
        lat: String,
        lon: String,
    ): Single<List<ForecastWeekModel>>

    fun getForecastTodayCurrentLocation(
        lat: String,
        lon: String,
    ): Single<ForecastTodayModel>
}