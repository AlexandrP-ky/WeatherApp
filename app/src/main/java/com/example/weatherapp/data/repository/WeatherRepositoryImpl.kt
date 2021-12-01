package com.example.weatherapp.data.repository


import com.example.weatherapp.core.RetrofitInstance
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

class WeatherRepositoryImpl(private val coordStorage: CoordStorage) : WeatherRepository {

    private val retrofitInstance: WeatherService =
        RetrofitInstance.getRetrofitInstance().create(WeatherService::class.java)

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

    override fun saveCoord(cityName: String, apiKey: String): Single<CoordModel> {
        return retrofitInstance.getCoord(cityName, apiKey).map { mapToCoordModel(it) }
    }

    override fun getForecastToday(
        cityName: String,
        units: String,
        lang: String,
        apiKey: String,
    ): Single<ForecastTodayModel> {
        return retrofitInstance.getForecastToday(cityName, units, lang, apiKey)
            .map { mapToForecastTodayModel(it) }
    }

    override fun getForecastTodayCurrentLocation(
        lat: String,
        lon: String,
        exclude: String,
        units: String,
        apiKey: String
    ): Single<ForecastTodayModel> {
        return retrofitInstance.getForecastCurrentToday(
            lat,
            lon,
            exclude,
            units,
            apiKey
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
        exclude: String,
        units: String,
        apiKey: String
    ): Single<List<ForecastWeekModel>> {
        return retrofitInstance
            .getForecastWeek(
                coordStorage.get().lat,
                coordStorage.get().lon,
                exclude,
                units,
                apiKey
            )
            .map { mapToListWeekForecastModel(it) }
    }

    override fun getForecastWeekCurrentLocation(
        lat: String,
        lon: String,
        exclude: String,
        units: String,
        apiKey: String
    ): Single<List<ForecastWeekModel>> {
        return retrofitInstance
            .getForecastWeek(
                lat,
                lon,
                exclude,
                units,
                apiKey
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