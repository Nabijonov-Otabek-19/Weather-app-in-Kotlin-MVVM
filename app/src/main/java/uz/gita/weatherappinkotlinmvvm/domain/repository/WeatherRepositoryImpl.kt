package uz.gita.weatherappinkotlinmvvm.domain.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.weatherappinkotlinmvvm.data.common.current.CurrentData
import uz.gita.weatherappinkotlinmvvm.data.common.forecast.ForecastData
import uz.gita.weatherappinkotlinmvvm.data.response.current.toData
import uz.gita.weatherappinkotlinmvvm.data.response.forecast.toData
import uz.gita.weatherappinkotlinmvvm.data.source.remote.apis.CurrentApi

class WeatherRepositoryImpl private constructor(
    private val currentApi: CurrentApi
) : WeatherRepository {

    private val API_KEY = "b1f04b7df0e9422994080010233004"

    companion object {
        private var weatherRepository: WeatherRepositoryImpl? = null

        fun init(currentApi: CurrentApi) {
            if (weatherRepository == null) {
                weatherRepository = WeatherRepositoryImpl(currentApi)
            }
        }

        fun getInstance(): WeatherRepositoryImpl = weatherRepository!!
    }

    override fun loadCurrentWeatherByCity(cityName: String): Flow<Result<CurrentData>> = flow {
        val response = currentApi.getCurrentWeatherByCity(API_KEY, cityName)
        when (response.code()) {
            in 200..299 -> {
                val currentResponse = response.body() ?: return@flow
                val locationData = currentResponse.location.toData()
                val currentData = currentResponse.current.toData(locationData)
                emit(Result.success(currentData))
            }

            else -> emit(Result.failure(Exception("No Response")))
        }
    }
        .catch { emit(Result.failure(it)) }
        .flowOn(Dispatchers.IO)

    override fun loadForecastWeatherByCity(cityName: String)
            : Flow<Result<ForecastData>> = flow {

        val response = currentApi.getForecastWeatherByCity(API_KEY, cityName)

        when (response.code()) {
            in 200..299 -> {
                val forecastResponse = response.body() ?: return@flow
                emit(Result.success(forecastResponse.forecast.toData()))
            }

            else -> emit(Result.failure(Exception("No Response")))
        }
    }
        .catch { emit(Result.failure(it)) }
        .flowOn(Dispatchers.IO)
}