package ru.gb.weatherappkotlin.view.weatherlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.gb.weatherappkotlin.R
import ru.gb.weatherappkotlin.databinding.FragmentWeatherListBinding
import ru.gb.weatherappkotlin.domain.Weather
import ru.gb.weatherappkotlin.view.weatherdetails.OnItemClick
import ru.gb.weatherappkotlin.view.weatherdetails.WeatherDetailsFragment
import ru.gb.weatherappkotlin.viewmodel.AppState

class WeatherListFragment : Fragment(), OnItemClick {

    companion object {
        fun newInstance() = WeatherListFragment()
    }

    var isRussian = true

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

        binding.weatherListFragmentFAB.setOnClickListener {
            isRussian = !isRussian
            if (isRussian) {
                viewModel.getWeatherForRussia()
                binding.weatherListFragmentFAB.setImageResource(R.drawable.ic_russia)
            } else {
                viewModel.getWeatherForWorld()
                binding.weatherListFragmentFAB.setImageResource(R.drawable.ic_earth)
            }
        }
        viewModel.getWeatherForRussia()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Loading -> {}

            is AppState.Error -> {
                Toast.makeText(
                    requireContext(),
                    "При загрузке данных произошла ошибка",
                    Toast.LENGTH_LONG
                ).show()
            }

            is AppState.SuccessCurrentCity -> {
                val result = appState.weatherData
            }
            is AppState.SuccessCitiesList -> {
                binding.mainFragmentRecyclerView.adapter =
                    WeatherListRVAdapter(appState.weatherList, this)
            }
        }
    }

    override fun onItemClick(weather: Weather) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .hide(this)
            .add(R.id.container, WeatherDetailsFragment.newInstance(weather)).addToBackStack("")
            .commit()
    }

}