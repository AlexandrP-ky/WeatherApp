package com.example.weatherapp.data.responce.weekforecastresponsemodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("description")
    @Expose
    val description: String,
    @SerializedName("main")
    @Expose
    val main: String
)
