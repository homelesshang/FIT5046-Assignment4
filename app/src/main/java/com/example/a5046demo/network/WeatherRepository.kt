package com.example.a5046demo.network

import com.example.a5046demo.model.WeatherResponse

class WeatherRepository {
    private val api = WeatherService.api
    private val API_KEY = "76458cb15c1e2d4d27ccf8ea5f8744c0"

    suspend fun getWeather(city: String): WeatherResponse {
        return api.getWeather(city, API_KEY)
    }
}