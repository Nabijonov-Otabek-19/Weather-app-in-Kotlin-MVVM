package uz.gita.weatherappinkotlinmvvm.data.source.remote

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.gita.weatherappinkotlinmvvm.data.source.remote.apis.CurrentApi

object Network {

    const val BASE_URL = "http://api.weatherapi.com/v1/"

    fun createClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor.Builder(context).build())
            .build()
    }

    fun createRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl(BASE_URL)
            .build()
    }

    fun getCurrentApi(retrofit: Retrofit): CurrentApi = retrofit.create(CurrentApi::class.java)
}