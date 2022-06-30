package ru.gb.weatherappkotlin.domain

data class Weather(
    val city: City = getDefaultCity(),
    val temperature: Int = 25,
    val feelsLike: Int = 27
)

data class City(
    val cityName: String,
    val lat: Double,
    val lon: Double
)

fun getDefaultCity() = City("Москва", 55.755826, 37.617299900000035)
