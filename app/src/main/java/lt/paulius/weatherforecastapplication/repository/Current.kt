package lt.paulius.weatherforecastapplication.repository

import com.google.gson.annotations.SerializedName

data class Current(
    @SerializedName("temp_c")
    val currentTemperature: Double,
    @SerializedName("wind_kph")
    val windSpeed: Double,
    @SerializedName("feelslike_c")
    val feelsLike: Double,
    @SerializedName("wind_dir")
    val windDirection: String,
    @SerializedName("uv")
    val uv: Double,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("last_updated")
    val lastUpdated: String,
    @SerializedName("condition")
    val condition: Condition
)
