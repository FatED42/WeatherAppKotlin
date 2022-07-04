package ru.gb.weatherappkotlin.view.weatherdetails

import ru.gb.weatherappkotlin.domain.Weather

fun interface OnItemClick {
    fun onItemClick(weather: Weather)
}