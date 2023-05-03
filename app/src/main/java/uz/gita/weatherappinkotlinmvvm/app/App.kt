package uz.gita.weatherappinkotlinmvvm.app

import android.app.Application
import uz.gita.weatherappinkotlinmvvm.data.source.local.SharedPref
import uz.gita.weatherappinkotlinmvvm.data.source.remote.Network
import uz.gita.weatherappinkotlinmvvm.repositories.impl.WeatherRepositoryImpl

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        val client = Network.createClient(this)
        val retrofit = Network.createRetrofit(client)
        val currentApi = Network.getCurrentApi(retrofit)
        WeatherRepositoryImpl.init(currentApi)

        SharedPref.init(this)
    }
}