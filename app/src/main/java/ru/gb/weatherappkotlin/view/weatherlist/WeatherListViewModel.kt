package ru.gb.weatherappkotlin.view.weatherlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.gb.weatherappkotlin.model.Repository
import ru.gb.weatherappkotlin.model.RepositoryLocalImpl
import ru.gb.weatherappkotlin.model.RepositoryRemoteImpl
import ru.gb.weatherappkotlin.viewmodel.AppState
import kotlin.random.Random

class WeatherListViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData<AppState>()
) : ViewModel() {

    lateinit var repository: Repository

    fun getLiveData(): MutableLiveData<AppState> {
        choiceRepository()
        return liveData
    }

    private fun choiceRepository() {
        if (isConnection()) {
            repository = RepositoryRemoteImpl()
        } else {
            repository = RepositoryLocalImpl()
        }
    }

    fun sentRequest() {
        liveData.value = AppState.Loading
        val rand = Random(System.nanoTime())
        if ((0..3).random(rand) == 1) {
            liveData.postValue(AppState.Error(throw IllegalStateException("Something went wrong")))
        } else {
            liveData.postValue(
                AppState.Success(
                    repository.getWeather(
                        55.755826,
                        37.617299900000035
                    )
                )
            )
        }
    }

    private fun isConnection(): Boolean {
        return false
    }

}