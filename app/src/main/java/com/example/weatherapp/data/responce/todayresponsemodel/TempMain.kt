package com.example.weatherapp.data.responce.todayresponsemodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TempMain(
    @SerializedName("temp")
    @Expose
    val temp: Double?,
    @SerializedName("temp_min")
    @Expose
    val temp_min: Double?,
    @SerializedName("temp_max")
    @Expose
    val temp_max: Double?,
    @SerializedName("feels_like")
    @Expose
    val feels_like: Double?
)
