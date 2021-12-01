package com.example.weatherapp.data.responce.todayresponsemodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TodayAndCoordResponseModel(
    @SerializedName("main")
    @Expose
    var main: TempMain?,
    @SerializedName("coord")
    @Expose
    var coord: Coord?,
    @SerializedName("dt")
    @Expose
    val dt: Int,
    @SerializedName("weather")
    @Expose
    val weather: List<Weather>,
    @SerializedName("name")
    @Expose
    val name: String?
)