package ru.gb.weatherappkotlin.model

import ru.gb.weatherappkotlin.domain.Weather
import ru.gb.weatherappkotlin.domain.getRussianCities
import ru.gb.weatherappkotlin.domain.getWorldCities

class RepositoryLocalImpl : RepositoryCurrentCityWeather, RepositoryCitiesListWeather {
    override fun getWeather(lat: Double, lon: Double) = Weather()

    override fun getWeatherList(location: WeatherLocation): List<Weather> {
        return when (location) {
            WeatherLocation.Russia -> {
                getRussianCities()
            }
            WeatherLocation.World -> {
                getWorldCities()
            }
        }
    }
}