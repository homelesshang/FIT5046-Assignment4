package com.example.a5046demo.model

/**
 * Represents the full response from a weather API (e.g., OpenWeatherMap).
 *
 * @param name The name of the city or location.
 * @param main Main weather-related values such as temperature.
 * @param weather A list containing weather condition details (usually only one item).
 */
data class WeatherResponse(
    val name: String,
    val main: Main,
    val weather: List<Weather>
)

/**
 * Contains core weather metrics like current temperature.
 *
 * @param temp Current temperature in degrees Celsius (or Fahrenheit, depending on API settings).
 */
data class Main(
    val temp: Float
)

/**
 * Describes the weather condition in human-readable format (e.g., "clear sky", "rain").
 *
 * @param description A short description of the weather condition.
 */
data class Weather(
    val description: String
)
