package ru.gb.weatherappkotlin.model

import ru.gb.weatherappkotlin.domain.Weather

class RepositoryLocalImpl : Repository {
    override fun getWeather(lat: Double, lon: Double): Weather {
        return Weather()
    }

    override fun getWeatherList(): List<Weather> {
        return listOf(Weather())
    }
}