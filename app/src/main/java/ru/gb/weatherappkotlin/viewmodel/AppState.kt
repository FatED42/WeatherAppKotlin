package ru.gb.weatherappkotlin.viewmodel

import ru.gb.weatherappkotlin.domain.Weather

sealed class AppState {

    data class Success(val weatherData: Weather) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()

}