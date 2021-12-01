package com.example.weatherapp.data.api

import com.example.weatherapp.data.responce.todayresponsemodel.TodayAndCoordResponseModel
import com.example.weatherapp.data.responce.weekforecastresponsemodel.WeekForecastResponseModel
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("weather")
    fun getCoord(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String
    ): Single<TodayAndCoordResponseModel>

    @GET("weather")
    fun getForecastToday(
        @Query("q") cityName: String,
        @Query("units") units: String,
        @Query("lang") lang: String,
        @Query("appid") apiKey: String
    ): Single<TodayAndCoordResponseModel>

    @GET("weather")
    fun getForecastCurrentToday(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("units") units: String,
        @Query("lang") lang: String,
        @Query("appid") apiKey: String
    ): Single<TodayAndCoordResponseModel>

    @GET("onecall")
    fun getForecastWeek(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("exclude") exclude: String,
        @Query("units") units: String,
        @Query("appid") apiKey: String
    ): Single<WeekForecastResponseModel>
}