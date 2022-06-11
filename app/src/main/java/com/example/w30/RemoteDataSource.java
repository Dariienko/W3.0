package com.example.w30;

import android.util.Log;

import com.example.w30.data.List;
import com.example.w30.data.WeekWeather;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteDataSource {
    private WeatherWeekService weatherService;
    public RemoteDataSource(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherService = retrofit.create(WeatherWeekService.class);
    }

    public WeekWeather getWeatherWeek(String s){
        Call<WeekWeather> call = weatherService.getWeatherWeek(s,"3d822b9dce4e57f12b9b3400d480a358");
        try {
            Response<WeekWeather> response = call.execute();
            if(response.isSuccessful()){
                for(int i = 0; i < response.body().getList().size(); i++) {
                    Log.d("RemoteData", String.valueOf(response.body().getList().get(i).getMain().getTemp()));
                }
                return response.body();
            }
        }catch(IOException ioex){
            Log.e("Remote", "IOEX: " + ioex.toString());
        }
        return null;
    }

}
