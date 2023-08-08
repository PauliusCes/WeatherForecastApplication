package lt.paulius.weatherforecastapplication.repository

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v1/forecast.json")
    suspend fun getWeather(
        @Query("key")
        key: String = "b746a61a054c43d89d323450221103",
        @Query("q")
        query: String,
        @Query("days")
        days: Int = 4,
        @Query("aqi")
        aqi: String = "no",
        @Query("alerts")
        alerts: String = "no"
    ): Response<Weather>
}