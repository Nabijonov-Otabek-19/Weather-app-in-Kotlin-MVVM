package uz.gita.weatherappinkotlinmvvm.repositories.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import uz.gita.weatherappinkotlinmvvm.data.common.CurrentData
import uz.gita.weatherappinkotlinmvvm.data.response.toData
import uz.gita.weatherappinkotlinmvvm.data.source.remote.apis.CurrentApi
import uz.gita.weatherappinkotlinmvvm.repositories.WeatherRepository

class WeatherRepositoryImpl private constructor(
    private val currentApi: CurrentApi
) : WeatherRepository {

    private val API_KEY = "b1f04b7df0e9422994080010233004"

    companion object {
        private var weatherRepository: WeatherRepository? = null

        fun init(currentApi: CurrentApi) {
            if (weatherRepository == null) {
                weatherRepository = WeatherRepositoryImpl(currentApi)
            }
        }

        fun getInstance(): WeatherRepository = weatherRepository!!
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
}