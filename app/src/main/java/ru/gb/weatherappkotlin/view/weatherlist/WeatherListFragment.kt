package ru.gb.weatherappkotlin.view.weatherlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.gb.weatherappkotlin.databinding.FragmentWeatherListBinding
import ru.gb.weatherappkotlin.viewmodel.AppState

class WeatherListFragment : Fragment() {

    companion object {
        fun newInstance() = WeatherListFragment()
    }

    lateinit var viewModel: WeatherListViewModel

    private var _binding: FragmentWeatherListBinding? = null
    private val binding: FragmentWeatherListBinding
        get() {
            return _binding!!
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(WeatherListViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, object : Observer<AppState> {
            override fun onChanged(t: AppState) {
                renderData(t)
            }
        })
        viewModel.sentRequest()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            AppState.Loading -> binding.loadingLayout.visibility = View.VISIBLE

            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Toast.makeText(
                    requireContext(),
                    "При загрузке данных произошла ошибка",
                    Toast.LENGTH_LONG
                ).show()
            }

            is AppState.Success -> {
                val result = appState.weatherData
                binding.cityName.text = result.city.cityName
                binding.temperatureValue.text = result.temperature.toString()
                binding.feelsLikeValue.text = result.feelsLike.toString()
                binding.cityCoordinates.text = "${result.city.lat}, ${result.city.lon}"
                binding.loadingLayout.visibility = View.GONE
            }
        }
    }

}