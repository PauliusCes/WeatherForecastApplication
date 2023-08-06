package lt.paulius.weatherforecastapplication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import lt.paulius.weatherforecastapplication.repository.Weather
import lt.paulius.weatherforecastapplication.repository.WeatherRepository
import lt.paulius.weatherforecastapplication.repository.WeatherServiceClient

class WeatherViewModel : ViewModel() {

    private val _weatherData = MutableSharedFlow<Weather>()
    val weatherData: SharedFlow<Weather>
        get() = _weatherData
    private val apiService = WeatherServiceClient.providesApiService()
    private val repo = WeatherRepository(apiService)

    fun fetchWeatherData(cityName: String) = viewModelScope.launch {
        repo.getWeather(cityName).let { response ->
            Log.e("Vm", "I got $response")
            if (response.isSuccessful) {
                Log.e("Vm", "I got $response")
                response.body()?.let { _weatherData.emit(it) }
            } else {
                Log.d("Tag", "getWeather Error Response: ${response.message()}")
            }
        }
    }
}
