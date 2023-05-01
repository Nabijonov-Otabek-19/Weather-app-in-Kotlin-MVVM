package uz.gita.weatherappinkotlinmvvm.presentation.viewmodels

import androidx.lifecycle.LiveData
import uz.gita.weatherappinkotlinmvvm.data.common.CurrentData

interface DetailViewModel {
    val loadingLiveData: LiveData<Boolean>
    val successLiveData: LiveData<CurrentData>
    val errorLiveData: LiveData<String>

    fun loadWeather(cityName: String)
}