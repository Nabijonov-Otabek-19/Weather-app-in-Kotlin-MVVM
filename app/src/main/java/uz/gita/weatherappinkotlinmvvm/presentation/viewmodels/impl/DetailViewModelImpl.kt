package uz.gita.weatherappinkotlinmvvm.presentation.viewmodels.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.weatherappinkotlinmvvm.data.common.current.CurrentData
import uz.gita.weatherappinkotlinmvvm.data.common.forecast.ForecastdayData
import uz.gita.weatherappinkotlinmvvm.presentation.viewmodels.DetailViewModel
import uz.gita.weatherappinkotlinmvvm.repositories.impl.WeatherRepositoryImpl

class DetailViewModelImpl : DetailViewModel, ViewModel() {

    private val weatherRepository = WeatherRepositoryImpl.getInstance()

    override val loadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    override val successLiveData: MutableLiveData<CurrentData> = MutableLiveData()
    override val errorLiveData: MutableLiveData<String> = MutableLiveData()

    override val successForecastLiveData: MutableLiveData<List<ForecastdayData>> = MutableLiveData()
    override val errorForecastLiveData: MutableLiveData<String> = MutableLiveData()

    override fun loadWeather(cityName: String) {
        loadingLiveData.value = true

        weatherRepository.loadCurrentWeatherByCity(cityName)
            .onEach { loadingLiveData.value = false }
            .onEach { it.onSuccess { successLiveData.value = it } }
            .onEach { it.onFailure { errorLiveData.value = it.message } }
            .launchIn(viewModelScope)
    }

    override fun loadForecast(cityName: String) {
        weatherRepository.loadForecastWeatherByCity(cityName)
            .onEach { it.onSuccess { successForecastLiveData.value = it.forecastday } }
            .onEach { it.onFailure { errorForecastLiveData.value = it.message } }
            .launchIn(viewModelScope)
    }
}