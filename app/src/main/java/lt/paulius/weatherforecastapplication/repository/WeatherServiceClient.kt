package lt.paulius.weatherforecastapplication.repository

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherServiceClient {

    private const val BASE_URL = "http://api.weatherapi.com/"

    fun providesApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().create()
                )
            )
            .build()
            .create(ApiService::class.java)
    }
}