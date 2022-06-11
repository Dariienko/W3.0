package com.example.w30;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class WeatherEntity {
    @PrimaryKey
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "temp")
    public double temp;

    @ColumnInfo(name = "wind")
    public double wind;

    @ColumnInfo(name = "pressure")
    public double pressure;

    @ColumnInfo(name = "dateTime")
    public String dateTime;


}
