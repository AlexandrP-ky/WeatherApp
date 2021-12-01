package com.example.weatherapp.data.responce.weekforecastresponsemodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Current(
    @SerializedName("dt")
    @Expose
    val currentDt: Int,
    @SerializedName("temp")
    @Expose
    val currentTemp: Double

)