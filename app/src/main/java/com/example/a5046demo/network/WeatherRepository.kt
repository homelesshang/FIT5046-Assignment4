package com.example.a5046demo.network

import com.example.a5046demo.model.WeatherResponse

/**
 * Repository class that handles weather-related data operations.
 * Acts as an abstraction layer between the ViewModel/UI and the Weather API service.
 *
 * This class internally uses the Retrofit WeatherApi interface to fetch data from
 * an external weather service like OpenWeatherMap.
 */
class WeatherRepository {

    // Retrofit API interface for performing HTTP requests
    private val api = WeatherService.api

    // API key used to authenticate with the weather service
    private val API_KEY = "76458cb15c1e2d4d27ccf8ea5f8744c0"

    /**
     * Fetches the current weather for the specified city using the Weather API.
     *
     * @param city The name of the city (e.g., "Melbourne").
     * @return A WeatherResponse object containing temperature, description, etc.
     * @throws Exception If the network call fails or API returns an error.
     */
    suspend fun getWeather(city: String): WeatherResponse {
        return api.getWeather(city, API_KEY)
    }
}
