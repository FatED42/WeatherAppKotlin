package ru.gb.weatherappkotlin.model

import ru.gb.weatherappkotlin.domain.Weather

fun interface RepositoryCurrentCityWeather {
    fun getWeather(lat: Double, lon: Double): Weather
}

fun interface RepositoryCitiesListWeather {
    fun getWeatherList(location: WeatherLocation): List<Weather>
}

sealed class WeatherLocation {
    object Russia : WeatherLocation()
    object World : WeatherLocation()
}