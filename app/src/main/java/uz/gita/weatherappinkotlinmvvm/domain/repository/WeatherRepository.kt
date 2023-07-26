package uz.gita.weatherappinkotlinmvvm.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.weatherappinkotlinmvvm.data.common.current.CurrentData
import uz.gita.weatherappinkotlinmvvm.data.common.forecast.ForecastData

interface WeatherRepository {

    fun loadCurrentWeatherByCity(cityName: String): Flow<Result<CurrentData>>

    fun loadForecastWeatherByCity(cityName: String): Flow<Result<ForecastData>>
}