package uz.gita.weatherappinkotlinmvvm.data.common.forecast


data class ForecastData(
    val forecastday: List<ForecastdayData>
)

data class ForecastdayData(
    val astro: AstroData,
    val hour: List<HourData>
)

data class ConditionData(
    val icon: String
)

data class HourData(
    val temp_c: Double,
    val time: String,
    val condition: ConditionData
)

data class AstroData(
    val moonrise: String,
    val moonset: String,
    val sunrise: String,
    val sunset: String
)