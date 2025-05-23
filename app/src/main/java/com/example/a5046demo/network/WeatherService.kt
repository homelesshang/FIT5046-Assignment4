package com.example.a5046demo.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Singleton object that initializes and provides access to the WeatherApi Retrofit interface.
 * This service handles all network communication with the OpenWeatherMap API.
 *
 * The Retrofit instance is configured with:
 * - Base URL: https://api.openweathermap.org/data/2.5/
 * - Gson converter for automatic JSON deserialization
 */
object WeatherService {

    /**
     * Lazily initialized Retrofit-based implementation of the WeatherApi interface.
     * Use this to make weather-related API calls.
     */
    val api: WeatherApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/") // Base endpoint for OpenWeatherMap API
            .addConverterFactory(GsonConverterFactory.create())   // Converts JSON responses into data classes
            .build()
            .create(WeatherApi::class.java)
    }
}
