package com.example.weatherapp.data.responce.todayresponsemodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TempMain(
    @SerializedName("temp")
    @Expose
    var temp: Double?,
    @SerializedName("temp_min")
    @Expose
    var temp_min: Double?,
    @SerializedName("temp_max")
    @Expose
    var temp_max: Double?,
    @SerializedName("feels_like")
    @Expose
    var feels_like: Double?
)
