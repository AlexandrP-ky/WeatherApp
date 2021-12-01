package com.example.weatherapp.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.domain.model.ForecastWeekModel
import java.text.SimpleDateFormat
import java.util.*

class WeatherForecastAdapter(
    private val listForecast: List<ForecastWeekModel>
) : RecyclerView.Adapter<WeatherForecastAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        val dayOfWeek: TextView = view.findViewById(R.id.day_of_week)
        val tempMax: TextView = view.findViewById(R.id.temp_max)
        val tempMin: TextView = view.findViewById(R.id.temp_min)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_forecast_weather, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = listForecast[position]

        val sdf = SimpleDateFormat("EEEE")
        val dateFormat = Date(item.dt.toLong() * 1000)
        val weekday: String = sdf.format(dateFormat)

        holder.dayOfWeek.text = weekday.replaceFirstChar { if (it.isLowerCase())
            it.titlecase(Locale.getDefault()) else it.toString() }
        holder.tempMax.text = item.max.toInt().toString()
        holder.tempMin.text = item.min.toInt().toString()
    }

    override fun getItemCount() = listForecast.size
}