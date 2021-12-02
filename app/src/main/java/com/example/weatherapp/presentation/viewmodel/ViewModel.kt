package com.example.weatherapp.presentation.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.domain.interactor.WeatherInteractor
import com.example.weatherapp.domain.model.ForecastTodayModel
import com.example.weatherapp.domain.model.ForecastWeekModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class ViewModel(
    private val interactor: WeatherInteractor
) : ViewModel() {

    private val _greeting = MutableLiveData<Boolean>()
    val greeting: LiveData<Boolean> = _greeting

    private val _currentDay = MutableLiveData<String>()
    val currentDay: LiveData<String> = _currentDay

    private val _tempToday = MutableLiveData<String>()
    val tempToday: LiveData<String> = _tempToday

    private val _forecastToday = MutableLiveData<ForecastTodayModel>()
    val forecastToday: LiveData<ForecastTodayModel> = _forecastToday

    private val _forecastWeek = MutableLiveData<List<ForecastWeekModel>>()
    val forecastWeek: LiveData<List<ForecastWeekModel>> = _forecastWeek

    fun getCoord(cityName: String) {
        interactor.getCoord(cityName)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
            }, { error ->
                error.printStackTrace()
            })
    }

    fun setVisible() {
        _greeting.postValue(false)
    }

    fun getForecastToday(cityName: String) {
        interactor.getForecastToday(cityName)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                _forecastToday.postValue(result)
                _tempToday.postValue(result.temp?.toInt().toString() + " \u2103")
                _currentDay.postValue(convertDt(result.dt))
            }, { error ->
                error.printStackTrace()
            })
    }

    fun getForecastWeek() {
        interactor.getForecastWeek()
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
        interactor.getForecastTodayCurrentLocation(lat = lat, lon = lon)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                _forecastToday.postValue(result)
                _tempToday.postValue(result.temp?.toInt().toString() + " \u2103")
                _currentDay.postValue(convertDt(result.dt))
            }, { error ->
                error.printStackTrace()
            })
    }

    fun getForecastWeekCurrentLocation(
        lat: String,
        lon: String
    ) {
        interactor.getForecastWeekCurrentLocation(lat = lat, lon = lon)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                _forecastWeek.postValue(result)
            }, { error ->
                error.printStackTrace()
            })
    }

    @SuppressLint("SimpleDateFormat")
    fun convertDt(dt: Int): String{
        val sdf = SimpleDateFormat("EEEE")
        val dateFormat = Date(dt.toLong() * 1000)
        val weekday = sdf.format(dateFormat)
        val textDt = weekday.replaceFirstChar {
            if (it.isLowerCase())
                it.titlecase(Locale.getDefault()) else it.toString()
        }
        return textDt
    }

}