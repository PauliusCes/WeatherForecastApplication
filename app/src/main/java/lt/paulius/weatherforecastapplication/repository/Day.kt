package lt.paulius.weatherforecastapplication.repository

import com.google.gson.annotations.SerializedName

data class Day(
    @SerializedName("maxtemp_c")
    val maxTempOfGivenDay: Double,
    @SerializedName("mintemp_c")
    val minTempOfGivenDay: Double,
    @SerializedName("avgtemp_c")
    val avgTempOfGivenDay: Double
)
