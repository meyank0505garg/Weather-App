package com.example.weatherforcast.network

import com.example.weatherforcast.model.Weather
import com.example.weatherforcast.model.WeatherObject
import com.example.weatherforcast.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton


@Singleton
interface WeatherApi {
    @GET(value = "data/2.5/forecast/daily")
    suspend fun getWeather(
        @Query("q") query: String = "Lisbon",
        @Query("units") units : String = "imperial",
        @Query("appid") appid : String = Constants.API_KEY

    ) : Weather
}

