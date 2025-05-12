package com.example.a5046demo.network

import retrofit2.http.GET
import retrofit2.http.Query
import com.example.a5046demo.model.WeatherResponse


interface WeatherApi {
    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): WeatherResponse
}