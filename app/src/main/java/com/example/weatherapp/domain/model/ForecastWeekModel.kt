package com.example.weatherapp.domain.model

data class ForecastWeekModel(
    val dt: Int,
    val max: Double,
    val min: Double,
    val main: String
)
