package uz.gita.weatherappinkotlinmvvm.data.common.current

data class CurrentData(
    val condition: ConditionData,
    val humidity: Int,
    val last_updated: String,
    val temp_c: Double,
    val locationData: LocationData
)

data class ConditionData(
    val icon: String,
    val text: String
)

data class LocationData(
    val country: String,
    val localtime: String,
    val name: String,
    val region: String
)