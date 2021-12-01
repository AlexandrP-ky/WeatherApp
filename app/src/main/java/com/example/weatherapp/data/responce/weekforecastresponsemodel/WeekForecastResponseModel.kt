package com.example.weatherapp.data.responce.weekforecastresponsemodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WeekForecastResponseModel(
    @SerializedName("timezone")
    @Expose
    val timezone: String,
    @SerializedName("current")
    @Expose
    val current: Current,
    @SerializedName("daily")
    @Expose
    val daily: List<Daily>
)