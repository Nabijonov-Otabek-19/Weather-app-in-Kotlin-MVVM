package uz.gita.weatherappinkotlinmvvm.data.source.remote.apis

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import uz.gita.weatherappinkotlinmvvm.data.response.CurrentResponse

interface CurrentApi {
    @GET("current.json")
    suspend fun getCurrentWeatherByCity(
        @Query("key") key: String,
        @Query("q") cityName: String
    ): Response<CurrentResponse>
}