package com.example.weatherapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.domain.interactor.WeatherInteractor
import com.example.weatherapp.domain.model.ForecastTodayModel
import com.example.weatherapp.domain.model.ForecastWeekModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

object NetworkService {
    const val API_KEY_VALUE = "ac31c579d79ae3bf73e3f5bcad390c49"
    const val EXCLUDE = "minutely,hourly"
    const val UNITS = "metric"
    const val LANG = "ru"
}

class ViewModel(
    private val interactor: WeatherInteractor
) : ViewModel() {

    private val _greeting = MutableLiveData<Boolean>()
    val greeting: LiveData<Boolean> = _greeting

    private val _forecastToday = MutableLiveData<ForecastTodayModel>()
    val forecastToday: LiveData<ForecastTodayModel> = _forecastToday

    private val _forecastWeek = MutableLiveData<List<ForecastWeekModel>>()
    val forecastWeek: LiveData<List<ForecastWeekModel>> = _forecastWeek

    fun getCoord(cityName: String) {
        interactor.getCoord(cityName, NetworkService.API_KEY_VALUE)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
            }, { error ->
                error.printStackTrace()
            })
    }

    fun setVisible(){
        _greeting.postValue(false)
    }

    fun getForecastToday(cityName: String) {
        interactor.getForecastToday(
            cityName,
            NetworkService.UNITS,
            NetworkService.LANG,
            NetworkService.API_KEY_VALUE
        )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                _forecastToday.postValue(result)
            }, { error ->
                error.printStackTrace()
            })
    }

    fun getForecastWeek() {
        interactor.getForecastWeek(
            NetworkService.EXCLUDE,
            NetworkService.UNITS,
            NetworkService.API_KEY_VALUE
        )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                _forecastWeek.postValue(result)
            }, { error ->
                error.printStackTrace()
            })
    }

    fun getForecastTodayCurrentLocation(
        lat: String,
        lon: String
    ) {
        interactor.getForecastTodayCurrentLocation(
            lat = lat,
            lon = lon,
            NetworkService.UNITS,
            NetworkService.LANG,
            NetworkService.API_KEY_VALUE
        )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                _forecastToday.postValue(result)
            }, { error ->
                error.printStackTrace()
            })
    }

    fun getForecastWeekCurrentLocation(
        lat: String,
        lon: String
    ) {
        interactor.getForecastWeekCurrentLocation(
            lat = lat,
            lon = lon,
            NetworkService.EXCLUDE,
            NetworkService.UNITS,
            NetworkService.API_KEY_VALUE
        )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                _forecastWeek.postValue(result)
            }, { error ->
                error.printStackTrace()
            })
    }

}