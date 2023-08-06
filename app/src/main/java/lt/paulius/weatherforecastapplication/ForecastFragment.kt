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
import kotlinx.coroutines.launch
import lt.paulius.weatherforecastapplication.databinding.FragmentForecastBinding

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
                viewModel.weatherData.collect { current ->
                    binding.apply {
                        currentTemperature.text = "${current.current.currentTemperature.toInt()}°"
                        windInformation.text = "${current.current.windSpeed.toInt()} km/h ${current.current.windDirection}"
                        feelsLike.text = "Feels like ${current.current.feelsLike.toInt()}°"
                        uv.text = "UV${current.current.uv.toInt()}"
                        humidity.text = "${current.current.humidity}%"
                        location.text = cityName
                        updateTime.text = "Updated ${current.current.lastUpdated.substring(11, 16)}"
                        condition.text = current.current.condition.weatherCondition
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}