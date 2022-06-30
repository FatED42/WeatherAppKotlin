package ru.gb.weatherappkotlin.model

import ru.gb.weatherappkotlin.domain.Weather

class RepositoryRemoteImpl : Repository {
    override fun getWeather(lat: Double, lon: Double): Weather {
        Thread {
            Thread.sleep(2000L)
        }.start()
        return Weather()
    }

    override fun getWeatherList(): List<Weather> {
        Thread {
            Thread.sleep(2000L)
        }.start()
        return listOf(Weather())
    }
}