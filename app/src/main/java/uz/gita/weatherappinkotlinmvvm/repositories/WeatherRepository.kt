package uz.gita.weatherappinkotlinmvvm.repositories

import kotlinx.coroutines.flow.Flow
import uz.gita.weatherappinkotlinmvvm.data.common.CurrentData

interface WeatherRepository {

    fun loadCurrentWeatherByCity(cityName: String): Flow<Result<CurrentData>>
}