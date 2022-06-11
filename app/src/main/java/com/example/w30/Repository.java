package com.example.w30;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.w30.data.WeekWeather;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

public class Repository {
    private LocalDataSource localDataSource;
    private RemoteDataSource remoteDataSource;
    private LiveData<List<WeatherEntity>> weatherFor4Day;
    public Repository(Context context){
        localDataSource = new LocalDataSource(context);
        remoteDataSource = new RemoteDataSource();
    }
    public void storeWeatherForWeek(WeekWeather example){
        for(int i=0;i<example.getList().size();i++) {
            WeatherEntity weatherEntity = new WeatherEntity();
            weatherEntity.id = i;
            weatherEntity.temp = example.getList().get(i).getMain().getTemp() - 273;
            weatherEntity.pressure = example.getList().get(i).getMain().getPressure();
            weatherEntity.dateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(example.getList().get(i).getDt() * 1000));
            weatherEntity.wind = example.getList().get(i).getWind().getSpeed();
            localDataSource.storeWeather(weatherEntity);
        }
    }
    private LiveData<List<WeatherEntity>> getIfNotSetWeatherforOneDay() {
        if (this.weatherFor4Day == null){
            this.weatherFor4Day = localDataSource.getWeatherfor4Day();
        }
        return this.weatherFor4Day;
    }

    public LiveData<List<WeatherEntity>> getWeekWeatherData(String s){// 1 день
        updateWeatherFromInternet(s);
        return getIfNotSetWeatherforOneDay();
    }

    private void updateWeatherFromInternet(String s) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                WeekWeather weatherForecast = remoteDataSource.getWeatherWeek(s);
//              Log.i("Jane",weatherForecast.getMain().getTemp().toString());
                storeWeatherForWeek(weatherForecast);
            }
        });
    }

}
