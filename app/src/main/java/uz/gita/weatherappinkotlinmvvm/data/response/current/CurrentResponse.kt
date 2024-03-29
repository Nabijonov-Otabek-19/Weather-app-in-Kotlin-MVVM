package uz.gita.weatherappinkotlinmvvm.data.response.current

import uz.gita.weatherappinkotlinmvvm.data.common.current.ConditionData
import uz.gita.weatherappinkotlinmvvm.data.common.current.CurrentData
import uz.gita.weatherappinkotlinmvvm.data.common.current.LocationData

data class CurrentResponse(
    val current: Current,
    val location: Location
)

data class Current(
    val cloud: Int,
    val condition: Condition,
    val feelslike_c: Double,
    val feelslike_f: Double,
    val gust_kph: Double,
    val gust_mph: Double,
    val humidity: Int,
    val is_day: Int,
    val last_updated: String,
    val last_updated_epoch: Int,
    val precip_in: Double,
    val precip_mm: Double,
    val pressure_in: Double,
    val pressure_mb: Double,
    val temp_c: Double,
    val temp_f: Double,
    val uv: Double,
    val vis_km: Double,
    val vis_miles: Double,
    val wind_degree: Int,
    val wind_dir: String,
    val wind_kph: Double,
    val wind_mph: Double
)

data class Location(
    val country: String,
    val lat: Double,
    val localtime: String,
    val localtime_epoch: Int,
    val lon: Double,
    val name: String,
    val region: String,
    val tz_id: String
)

data class Condition(
    val code: Int,
    val icon: String,
    val text: String
)

fun Location.toData() = LocationData(country, localtime, name, region)

fun Current.toData(locationData: LocationData) =
    CurrentData(condition.toData(), humidity, last_updated, temp_c, locationData)

fun Condition.toData() = ConditionData(icon, text)