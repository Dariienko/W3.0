package com.example.w30;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


public class Day1ViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<WeatherEntity>> weatherData;
    private String cityName;
    LiveData<List<WeatherEntity>> getWeatherData(String s) {
        cityName = s;
        weatherData = repository.getWeekWeatherData(cityName);
        return weatherData;
    }

    public Day1ViewModel (Application application) {
        super(application);
        repository = new Repository(application);
    }
}
