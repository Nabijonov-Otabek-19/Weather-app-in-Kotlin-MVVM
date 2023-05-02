package uz.gita.weatherappinkotlinmvvm.data.source.remote.apis

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import uz.gita.weatherappinkotlinmvvm.data.response.current.CurrentResponse
import uz.gita.weatherappinkotlinmvvm.data.response.forecast.ForecastResponse

interface CurrentApi {
    @GET("current.json")
    suspend fun getCurrentWeatherByCity(
        @Query("key") key: String,
        @Query("q") cityName: String
    ): Response<CurrentResponse>

    @GET("forecast.json")
    suspend fun getForecastWeatherByCity(
        @Query("key") key: String,
        @Query("q") cityName: String,
        @Query("days") day: Int = 1,
        @Query("aqi") aqi: String = "no",
        @Query("alerts") alert: String = "no"
    ): Response<ForecastResponse>

//    @GET("future.json")
//    suspend fun getForecastWeatherByCity(
//        @Query("key") key: String, @Query("q") cityName: String,
//        @Query("dt") data: String
//    ): Response<ForecastResponse>
}