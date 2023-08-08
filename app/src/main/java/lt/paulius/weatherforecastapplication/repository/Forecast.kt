package lt.paulius.weatherforecastapplication.repository

import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("forecastday")
    val forecastOfDay: List<Forecastday>
)
