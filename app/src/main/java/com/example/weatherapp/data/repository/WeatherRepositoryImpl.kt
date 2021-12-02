package com.example.weatherapp.data.repository

import com.example.weatherapp.data.api.WeatherService
import com.example.weatherapp.data.responce.todayresponsemodel.TodayAndCoordResponseModel
import com.example.weatherapp.data.responce.weekforecastresponsemodel.Daily
import com.example.weatherapp.data.responce.weekforecastresponsemodel.WeekForecastResponseModel
import com.example.weatherapp.data.storage.CoordStorage
import com.example.weatherapp.data.storage.Coords
import com.example.weatherapp.domain.model.CoordModel
import com.example.weatherapp.domain.model.ForecastWeekModel
import com.example.weatherapp.domain.model.ForecastTodayModel
import com.example.weatherapp.domain.repository.WeatherRepository
import io.reactivex.rxjava3.core.Single

const val API_KEY_VALUE = "ac31c579d79ae3bf73e3f5bcad390c49"
const val EXCLUDE = "minutely,hourly"
const val UNITS = "metric"
const val LANG = "ru"

class WeatherRepositoryImpl(
    private val coordStorage: CoordStorage,
    private val retrofitInstance: WeatherService
) : WeatherRepository {

    private fun mapToCoordModel(response: TodayAndCoordResponseModel): CoordModel {
        val coords = Coords(
            lat = response.coord?.lat.toString(),
            lon = response.coord?.lon.toString()
        )
        coordStorage.save(coords)
        return CoordModel(
            lon = response.coord?.lon.toString(),
            lat = response.coord?.lat.toString()
        )
    }

    override fun saveCoord(cityName: String): Single<CoordModel> {
        return retrofitInstance.getCoord(cityName, apiKey = API_KEY_VALUE)
            .map { mapToCoordModel(it) }
    }

    override fun getForecastToday(
        cityName: String,

        ): Single<ForecastTodayModel> {
        return retrofitInstance.getForecastToday(
            cityName,
            units = UNITS,
            lang = LANG,
            apiKey = API_KEY_VALUE
        )
            .map { mapToForecastTodayModel(it) }
    }

    override fun getForecastTodayCurrentLocation(
        lat: String,
        lon: String,
    ): Single<ForecastTodayModel> {
        return retrofitInstance.getForecastCurrentToday(
            lat,
            lon,
            units = UNITS,
            lang = LANG,
            apiKey = API_KEY_VALUE
        ).map { mapToForecastTodayCurrentLocation(it) }
    }

    private fun mapToForecastTodayCurrentLocation(responseModel: TodayAndCoordResponseModel):
            ForecastTodayModel {
        return ForecastTodayModel(
            responseModel.name,
            responseModel.dt,
            responseModel.main?.temp
        )
    }

    override fun getForecastWeek(
    ): Single<List<ForecastWeekModel>> {
        return retrofitInstance
            .getForecastWeek(
                coordStorage.get().lat,
                coordStorage.get().lon,
                exclude = EXCLUDE,
                units = UNITS,
                apiKey = API_KEY_VALUE
            )
            .map { mapToListWeekForecastModel(it) }
    }

    override fun getForecastWeekCurrentLocation(
        lat: String,
        lon: String,
    ): Single<List<ForecastWeekModel>> {
        return retrofitInstance
            .getForecastWeek(
                lat,
                lon,
                exclude = EXCLUDE,
                units = UNITS,
                apiKey = API_KEY_VALUE
            )
            .map { mapToListWeekForecastModel(it) }
    }

    private fun mapToListWeekForecastModel(response: WeekForecastResponseModel):
            List<ForecastWeekModel> {
        return response.daily.map { mapToWeekForecastModel(it) }
    }

    private fun mapToWeekForecastModel(daily: Daily):
            ForecastWeekModel {
        return ForecastWeekModel(
            daily.dt,
            daily.temp.max,
            daily.temp.min
        )
    }

    private fun mapToForecastTodayModel(responseModel: TodayAndCoordResponseModel):
            ForecastTodayModel {
        return ForecastTodayModel(
            responseModel.name,
            responseModel.dt,
            responseModel.main?.temp
        )
    }
}