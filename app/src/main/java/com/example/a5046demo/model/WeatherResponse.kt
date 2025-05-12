package com.example.a5046demo.model

data class WeatherResponse(
    val name: String,              // 城市名称，例如 Melbourne
    val main: Main,                // 主天气对象，包含温度
    val weather: List<Weather>     // 天气描述（是数组）
)

data class Main(
    val temp: Float                // 温度（摄氏度）
)

data class Weather(
    val description: String        // 天气描述，例如 "clear sky"
)