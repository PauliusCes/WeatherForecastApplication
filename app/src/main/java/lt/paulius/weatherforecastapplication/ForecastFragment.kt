package lt.paulius.weatherforecastapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import lt.paulius.weatherforecastapplication.databinding.FragmentForecastBinding
import lt.paulius.weatherforecastapplication.repository.Forecastday

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

        binding.progressBar.visibility = View.VISIBLE
        binding.constraintLayout.visibility = View.INVISIBLE

        val cityName = args.cityName
        viewModel.fetchWeatherData(cityName)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.weatherData.collect { weather ->
                    binding.apply {

                        delay(1000)
                        binding.progressBar.visibility = View.INVISIBLE
                        binding.constraintLayout.visibility = View.VISIBLE

                        currentTemperature.text =
                            "${weather.current.currentTemperature.toInt()}°"
                        windInformation.text =
                            "${weather.current.windSpeed.toInt()} km/h\n${weather.current.windDirection}"
                        feelsLike.text =
                            "Feels like ${weather.current.feelsLike.toInt()}°"
                        uv.text =
                            "UV ${weather.current.uv.toInt()}"
                        humidity.text =
                            "${weather.current.humidity}%"
                        location.text =
                            "$cityName, ${weather.location.country}"
                        updateTime.text =
                            "Updated ${weather.current.lastUpdated.substring(11, 16)}"
                        condition.text =
                            weather.current.condition.weatherCondition

                        val forecastDays = weather.forecast.forecastOfDay

                        if (forecastDays.size >= 4) {
                            updateForecastDay(
                                forecastDays[1],
                                firstDayDate,
                                firstDayAvgTemp,
                                firstDayHighLow
                            )
                            updateForecastDay(
                                forecastDays[2],
                                secondDayDate,
                                secondDayAvgTemp,
                                secondDayHighLow
                            )
                            updateForecastDay(
                                forecastDays[3],
                                thirdDayDate,
                                thirdDayAvgTemp,
                                thirdDayHighLow
                            )
                        }
                    }
                }
            }
        }
    }

    private fun updateForecastDay(
        forecastDay: Forecastday,
        dateTextView: TextView,
        avgTempTextView: TextView,
        highLowTextView: TextView
    ){
        dateTextView.text = forecastDay.date.substring(5, 10)
        avgTempTextView.text = "${forecastDay.day.avgTempOfGivenDay.toInt()}°"
        highLowTextView.text =
            "${forecastDay.day.maxTempOfGivenDay.toInt()}°/${forecastDay.day.minTempOfGivenDay.toInt()}°"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
