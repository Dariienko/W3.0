package com.example.w30;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.List;

public class LocalDataSource {
    final WeatherDB db;

    public LocalDataSource(Context context){
        db = Room.databaseBuilder(context, WeatherDB.class,"weather"). fallbackToDestructiveMigration().build();
    }
    public void storeWeather(WeatherEntity we) {
        db.weatherDao().insertWeather(we);
    }
    public LiveData<List<WeatherEntity>> getWeatherfor4Day(){
        //Log.d("Запрос из базы", String.valueOf(db.weatherDao().getWeatherForecast().getValue().get(0).id));
      return db.weatherDao().getWeekWeather();
    }

    public LiveData<WeatherEntity> getWeatherforOneDay(){
        //Log.d("Запрос из базы", String.valueOf(db.weatherDao().getOneDayWeather().getValue().id));
        return db.weatherDao().getOneDayWeather();

    }
}
