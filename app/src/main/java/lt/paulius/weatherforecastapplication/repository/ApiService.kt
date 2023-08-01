package lt.paulius.weatherforecastapplication.repository

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("v1/current.json?key=b746a61a054c43d89d323450221103&q=Vilnius&aqi=no")
    suspend fun getWeather(): Response<Weather>
}