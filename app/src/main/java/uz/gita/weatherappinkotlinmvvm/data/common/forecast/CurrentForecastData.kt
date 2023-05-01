package uz.gita.weatherappinkotlinmvvm.data.common.forecast


data class ForecastData(
    val forecastday: List<ForecastdayData>
)

data class ForecastdayData(
    val date: String,
    val hour: List<HourData>
)

data class HourData(
    val temp_c: Double,
    val time: String
)