package lt.paulius.weatherforecastapplication.repository

import com.google.gson.annotations.SerializedName

data class Forecastday(
    @SerializedName("date")
    val date: String,
    @SerializedName("day")
    val day: Day
)

