package com.example.a5046demo.network

import retrofit2.http.GET
import retrofit2.http.Query
import com.example.a5046demo.model.WeatherResponse

/**
 * Retrofit interface for accessing weather data from a RESTful weather API
 * such as OpenWeatherMap.
 *
 * Example endpoint: https://api.openweathermap.org/data/2.5/weather?q=Melbourne&appid=YOUR_API_KEY&units=metric
 */
interface WeatherApi {

    /**
     * Fetches the current weather information for the given city.
     *
     * @param city The name of the city (e.g., "Melbourne").
     * @param apiKey Your weather API key (from OpenWeatherMap or similar service).
     * @param units The unit system for temperature ("metric" for Celsius, "imperial" for Fahrenheit).
     * @return A WeatherResponse object containing temperature, description, and location info.
     */
    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): WeatherResponse
}
