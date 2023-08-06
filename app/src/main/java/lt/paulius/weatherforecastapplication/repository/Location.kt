package lt.paulius.weatherforecastapplication.repository

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("name")
    val city: String,
)
