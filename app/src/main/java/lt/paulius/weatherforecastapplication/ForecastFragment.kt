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
                viewModel.weatherData.collect { current ->
                    binding.apply {
                        currentTemperature.text = "${current.current.temp_c.toInt()}°"
                        windInformation.text = "${current.current.wind_kph.toInt()} km/h ${current.current.wind_dir}"
                        feelsLike.text = "Feels like ${current.current.feelslike_c.toInt()}°"
                        uv.text = "UV${current.current.uv.toInt()}"
                        humidity.text = "${current.current.humidity}%"
                        location.text = cityName
                        updateTime.text = "Updated ${current.current.last_updated.substring(11, 16)}"
                        condition.text = current.current.condition.text
                    }
                }
//                viewModel.fetchWeatherData(cityName).collect { current ->
//                    val temperature = current.current.temp_c.toInt()
//                    val windSpeed = current.current.wind_kph.toInt()
//                    val feelsLike = current.current.feelslike_c.toInt()
//                    val windDir = current.current.wind_dir
//                    val uv = current.current.uv.toInt()
//                    val humidity = current.current.humidity
//                    val updateTime = current.current.last_updated
//                    val formattedTime = updateTime.substring(11, 16)
//                    val condition = current.current.condition.text
//                    binding.currentTemperature.text = "$temperature°"
//                    binding.windInformation.text = "$windSpeed km/h $windDir"
//                    binding.feelsLike.text = "Feels like $feelsLike°"
//                    binding.uv.text = "UV$uv"
//                    binding.humidity.text = "$humidity%"
//                    binding.location.text = "$cityName"
//                    binding.updateTime.text = "Updated $formattedTime"
//                    binding.condition.text = "$condition"
//                }
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