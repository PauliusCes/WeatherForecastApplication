package lt.paulius.weatherforecastapplication.repository

import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("location")
    val location: Location,
    @SerializedName("current")
    val current: Current
)
