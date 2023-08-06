package lt.paulius.weatherforecastapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import lt.paulius.weatherforecastapplication.databinding.FragmentForecastBinding
import lt.paulius.weatherforecastapplication.databinding.FragmentSearchBinding

class ForecastFragment : Fragment() {

    private var _binding: FragmentForecastBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WeatherViewModel by viewModels()
    private val args by navArgs<ForecastFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForecastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cityName = args.cityName
        viewModel.fetchWeatherData(cityName)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.fetchWeatherData(cityName).collect { current ->
                    val temperature = current.current.temp_c.toInt()
                    val windSpeed = current.current.wind_kph.toInt()
                    val feelsLike = current.current.feelslike_c.toInt()
                    val windDir = current.current.wind_dir
                    binding.currentTemperature.text = "$temperature°"
                    binding.windInformation.text = "$windSpeed km/h $windDir"
                    binding.feelsLike.text = "Feels like $feelsLike°"
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = ForecastFragment()
        const val TAG = "forecast_fragment"
    }
}