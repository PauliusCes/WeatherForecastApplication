package lt.paulius.weatherforecastapplication.repository

data class Current(
    val temp_c: Double,
    val wind_kph: Double,
    val feelslike_c: Double,
    val wind_dir: String,
    val uv: Double
)
