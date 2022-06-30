package ru.gb.weatherappkotlin.model

import ru.gb.weatherappkotlin.domain.Weather

interface Repository {
    fun getWeather(lat: Double, lon: Double): Weather
    fun getWeatherList(): List<Weather>
}