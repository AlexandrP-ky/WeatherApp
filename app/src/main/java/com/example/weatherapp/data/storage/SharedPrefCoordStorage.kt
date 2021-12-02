package com.example.weatherapp.data.storage

import android.content.Context
import android.content.SharedPreferences

const val APP_PREFERENCES = "settings"
const val APP_PREFERENCES_LON = "lat"
const val APP_PREFERENCES_LAT = "lon"

class SharedPrefCoordStorage(context: Context) : CoordStorage {

    private val pref: SharedPreferences = context.getSharedPreferences(
        APP_PREFERENCES,
        Context.MODE_PRIVATE
    )

    override fun save(coords: Coords) {
        pref.edit().putString(APP_PREFERENCES_LAT, coords.lat).apply()
        pref.edit().putString(APP_PREFERENCES_LON, coords.lon).apply()
    }

    override fun get(): Coords {
        return Coords(
            lat = pref.getString(APP_PREFERENCES_LAT, "").toString(),
            lon = pref.getString(APP_PREFERENCES_LON, "").toString()
        )
    }
}