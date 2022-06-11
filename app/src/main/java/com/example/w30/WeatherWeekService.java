package com.example.w30;


import com.example.w30.data.WeekWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherWeekService {
    @GET("data/2.5/forecast")
    Call<WeekWeather> getWeatherWeek(@Query("q") String tittle , @Query("appid") String appId);
}
