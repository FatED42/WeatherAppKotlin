package ru.gb.weatherappkotlin.view.weatherlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import ru.gb.weatherappkotlin.R
import ru.gb.weatherappkotlin.databinding.FragmentWeatherListBinding
import ru.gb.weatherappkotlin.domain.Weather
import ru.gb.weatherappkotlin.view.weatherdetails.OnItemClick
import ru.gb.weatherappkotlin.view.weatherdetails.WeatherDetailsFragment
import ru.gb.weatherappkotlin.viewmodel.AppState
import ru.gb.weatherappkotlin.viewmodel.WeatherListViewModel

class WeatherListFragment : Fragment(), OnItemClick {

    companion object {
        fun newInstance() = WeatherListFragment()
    }

    private var isRussian = true

    private val viewModel: WeatherListViewModel by lazy {
        ViewModelProvider(this).get(WeatherListViewModel::class.java)
    }

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
        viewModel.getLiveData().observe(viewLifecycleOwner) { t -> renderData(t) }

        binding.weatherListFragmentFAB.setOnClickListener {
            whichListToChoose()
        }
        viewModel.getWeatherForRussia()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Loading -> {
                binding.loading()
            }

            is AppState.Error -> {
                binding.showResult()
                binding.root.showSnackbar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    Snackbar.LENGTH_LONG
                ) {
                    whichListToChoose()
                }
            }

            is AppState.SuccessCurrentCity -> {
                binding.showResult()
                appState.weatherData
            }
            is AppState.SuccessCitiesList -> {
                binding.showResult()
                binding.mainFragmentRecyclerView.adapter =
                    WeatherListRVAdapter(appState.weatherList, this)
            }
        }
    }

    private fun FragmentWeatherListBinding.loading() {
        this.mainFragmentLoadingLayout.visibility = View.VISIBLE
        this.weatherListFragmentFAB.visibility = View.GONE
    }

    private fun FragmentWeatherListBinding.showResult() {
        this.mainFragmentLoadingLayout.visibility = View.GONE
        this.weatherListFragmentFAB.visibility = View.VISIBLE
    }

    private fun View.showSnackbar(
        errorText: String,
        actionText: String,
        duration: Int,
        block: (View) -> Unit
    ) {
        Snackbar.make(this, errorText, duration).setAction(actionText, block).show()
    }

    override fun onItemClick(weather: Weather) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .hide(this)
            .add(R.id.container, WeatherDetailsFragment.newInstance(weather)).addToBackStack("")
            .commit()
    }

    private fun whichListToChoose() {
        isRussian = !isRussian
        with(viewModel) {
            if (isRussian) {
                getWeatherForRussia()
                binding.weatherListFragmentFAB.setImageResource(R.drawable.ic_russia)
            } else {
                getWeatherForWorld()
                binding.weatherListFragmentFAB.setImageResource(R.drawable.ic_earth)
            }
        }
    }
}