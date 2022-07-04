package ru.gb.weatherappkotlin.view.weatherlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.gb.weatherappkotlin.model.*
import ru.gb.weatherappkotlin.viewmodel.AppState
import kotlin.random.Random

class WeatherListViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData<AppState>()
) : ViewModel() {

    private lateinit var repositoryCitiesListWeather: RepositoryCitiesListWeather
    private lateinit var repositoryCurrentCityWeather: RepositoryCurrentCityWeather

    fun getLiveData(): MutableLiveData<AppState> {
        choiceRepository()
        return liveData
    }

    private fun choiceRepository() {
        if (isConnection()) {
            repositoryCurrentCityWeather = RepositoryRemoteImpl()
        } else {
            repositoryCitiesListWeather = RepositoryLocalImpl()
        }
    }

    fun getWeatherForRussia() {
        sentRequest(WeatherLocation.Russia)
    }

    fun getWeatherForWorld() {
        sentRequest(WeatherLocation.World)
    }

    private fun sentRequest(location: WeatherLocation) {
        liveData.value = AppState.Loading
        val rand = Random(System.nanoTime())
        if ((0..3).random(rand) == 1) {
            liveData.postValue(AppState.Error(IllegalStateException("Something went wrong")))
        } else {
            liveData.postValue(
                AppState.SuccessCitiesList(
                    repositoryCitiesListWeather.getWeatherList(location)
                )
            )
        }
    }

    private fun isConnection(): Boolean {
        return false
    }

}