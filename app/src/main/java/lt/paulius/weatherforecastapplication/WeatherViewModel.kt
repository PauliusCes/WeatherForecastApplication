package lt.paulius.weatherforecastapplication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import lt.paulius.weatherforecastapplication.repository.Condition
import lt.paulius.weatherforecastapplication.repository.Current
import lt.paulius.weatherforecastapplication.repository.Location
import lt.paulius.weatherforecastapplication.repository.Weather
import lt.paulius.weatherforecastapplication.repository.WeatherRepository
import lt.paulius.weatherforecastapplication.repository.WeatherServiceClient
import retrofit2.Response
import java.lang.Exception

class WeatherViewModel : ViewModel() {

    private val _weatherData = MutableSharedFlow<Weather>()

    //    val weatherData = _weatherData.asSharedFlow()
    val weatherData: SharedFlow<Weather>
        get() = _weatherData
    private val apiService = WeatherServiceClient.providesApiService()
    private val repo = WeatherRepository(apiService)

//    fun fetchWeatherData(cityName: String): Flow<Weather> {
//        return flow {
//            try {
//                val response = repo.getWeather(cityName)
//                if (response.isSuccessful) {
//                    response.body()?.let {
//                        emit(it)
//                    }
//                } else {
//                    emit(Weather(Location(""), Current(0.0, 0.0, 0.0, "", 0.0, 0, "0", Condition(""))))
//                }
//            } catch (e: Exception) {
//                emit(Weather(Location(""), Current(0.0, 0.0, 0.0, "", 0.0, 0, "0", Condition(""))))
//            }
//        }
//    }

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
