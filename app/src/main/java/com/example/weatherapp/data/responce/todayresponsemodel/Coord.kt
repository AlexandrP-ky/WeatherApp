package com.example.weatherapp.data.responce.todayresponsemodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Coord(
    @SerializedName("lon")
    @Expose
    var lon: Double,
    @SerializedName("lat")
    @Expose
    var lat: Double

)
