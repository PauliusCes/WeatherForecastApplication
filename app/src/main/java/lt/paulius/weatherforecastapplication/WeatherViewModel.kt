package lt.paulius.weatherforecastapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import lt.paulius.weatherforecastapplication.repository.Current
import lt.paulius.weatherforecastapplication.repository.Location
import lt.paulius.weatherforecastapplication.repository.Weather
import lt.paulius.weatherforecastapplication.repository.WeatherRepository
import lt.paulius.weatherforecastapplication.repository.WeatherServiceClient
import java.lang.Exception

class WeatherViewModel: ViewModel() {

    private val _weatherData = MutableSharedFlow<Weather>()
    val weatherData = _weatherData.asSharedFlow()
    private val apiService = WeatherServiceClient.providesApiService()
    private val repo = WeatherRepository(apiService)

    fun fetchWeatherData(cityName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repo.getWeather(cityName)
                if (response.isSuccessful) {
                    response.body()?.let { _weatherData.emit(it) }
                } else {
                    _weatherData.emit(Weather(Location(""), Current(0)))
                }
            } catch (e: Exception) {
                _weatherData.emit(Weather(Location(""), Current(0)))
            }
        }
    }
}