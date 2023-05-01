package uz.gita.weatherappinkotlinmvvm.presentation.viewmodels

import androidx.lifecycle.LiveData
import uz.gita.weatherappinkotlinmvvm.data.common.current.CurrentData
import uz.gita.weatherappinkotlinmvvm.data.common.forecast.ForecastData
import uz.gita.weatherappinkotlinmvvm.data.common.forecast.ForecastdayData

interface DetailViewModel {
    val loadingLiveData: LiveData<Boolean>
    val successLiveData: LiveData<CurrentData>
    val errorLiveData: LiveData<String>

    val successForecastLiveData: LiveData<List<ForecastdayData>>
    val errorForecastLiveData: LiveData<String>

    fun loadWeather(cityName: String)
    fun loadForecast(cityName: String)
}