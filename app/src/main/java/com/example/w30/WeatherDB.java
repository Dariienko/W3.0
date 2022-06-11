package com.example.w30;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {WeatherEntity.class},version=1)
public abstract class WeatherDB extends RoomDatabase {
    public static WeatherDB instance;
    public abstract WeatherDao weatherDao();
}