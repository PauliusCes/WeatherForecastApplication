package lt.paulius.weatherforecastapplication.repository

import com.google.gson.annotations.SerializedName

data class Condition(
    @SerializedName("text")
    val weatherCondition: String
)
