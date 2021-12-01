package com.example.weatherapp.data.storage



interface CoordStorage {
    fun save(coords: Coords)
    fun get():Coords
}