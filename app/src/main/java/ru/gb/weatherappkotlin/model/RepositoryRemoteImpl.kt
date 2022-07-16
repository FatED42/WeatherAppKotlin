package ru.gb.weatherappkotlin.model

import ru.gb.weatherappkotlin.domain.Weather

class RepositoryRemoteImpl : RepositoryCurrentCityWeather {
    override fun getWeather(lat: Double, lon: Double): Weather {
        Thread {
            Thread.sleep(2000L)
        }.start()
        return Weather()
    }
}