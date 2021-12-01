package com.example.weatherapp.data.responce.weekforecastresponsemodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Daily(
    @SerializedName("clouds")
    @Expose
    val clouds: Int,
    @SerializedName("dt")
    @Expose
    val dt: Int,
    @SerializedName("temp")
    @Expose
    val temp: Temp
)