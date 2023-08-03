package lt.paulius.weatherforecastapplication.repository

class WeatherRepository(private val apiService: ApiService) {
    suspend fun getWeather(location: String) = apiService.getWeather(query = location)
}