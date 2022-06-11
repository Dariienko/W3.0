package com.example.w30;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.w30.data.City;
import com.example.w30.data.WeekWeather;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements Adapter1.ItemClickListener {
    LiveData<List<WeatherEntity>> weatherData;
    private Day1ViewModel day1ViewModel;
    Adapter1 adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView;
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String city = "Odesa";
        EditText cityName = findViewById(R.id.city);
        cityName.setText(city);
        day1ViewModel = new ViewModelProvider(this).get(Day1ViewModel.class);
        weatherData = day1ViewModel.getWeatherData(city);
        weatherData.observe(this, new Observer<List<WeatherEntity>>() {
            @Override
            public void onChanged(List<WeatherEntity> weatherEntities) {
                if (weatherData.getValue() != null) {
                    for(int i=0;i<weatherData.getValue().size();i++) {
                        Log.i("Jane temp", String.valueOf(weatherData.getValue().get(i).temp));
                    }
                    adapter = new Adapter1(getApplicationContext(),weatherData.getValue());
                    adapter.setClickListener(MainActivity.this::onItemClick);
                            recyclerView.setAdapter(adapter);
                }
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        EditText myPressure = findViewById(R.id.pressure1);
        EditText myTextWind = findViewById(R.id.wind1);
        EditText myTextTemp = findViewById(R.id.temp1);
        EditText myData = findViewById(R.id.data1);
        myData.setText(adapter.getItem(position).dateTime);
        myTextWind.setText(String.valueOf(adapter.getItem(position).wind));
        myPressure.setText(String.valueOf(adapter.getItem(position).pressure));
        myTextTemp.setText(String.valueOf(adapter.getItem(position).temp));
    }
}

//        repository=new Repository(this);
//        weatherData = repository.getWeekWeatherData(city);
//        weatherData.observe(this, new Observer<List<WeatherEntity>>() {
//            @Override
//            public void onChanged(List<WeatherEntity> weatherEntities) {
//                if (weatherData.getValue().size()!= 0) {
//                    for(int i=0;i<weatherData.getValue().size();i++) {
//                        Log.i("Jane temp", String.valueOf(weatherData.getValue().get(i).temp));
//                        wind.setText(String.valueOf(weatherData.getValue().get(i).wind));
//                        temp.setText(String.valueOf(weatherData.getValue().get(i).temp));
//                    }
//                    Adapter1 adapter = new Adapter1(getApplicationContext(),weatherData.getValue());
//                            recyclerView.setAdapter(adapter);
//
//                }
//            }
//        });
//
//        RoomDatabase database= Room.databaseBuilder(getApplicationContext(),WeatherDB.class, "weather").// название файла
//                fallbackToDestructiveMigration()// дешевый способ миграции на новую версию
//                .build();
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://api.openweathermap.org")//базовая часть адреса
//                .addConverterFactory(GsonConverterFactory.create())//конвертер
//                .build();
//        WeatherWeekService weatherWeekService;
//        weatherWeekService =retrofit.create(WeatherWeekService.class);//создали объект, с его помощью будем отправлять запросы
//        weatherWeekService.getWeatherWeek("Kiev","3d822b9dce4e57f12b9b3400d480a358").
//                enqueue(new Callback<WeekWeather>() {//aссинхронный вызов (для синхронного был бы метод execute() )
//                    @Override
//                    public void onResponse(Call<WeekWeather> call, Response<WeekWeather> response) {
//                        if (response.isSuccessful()) {Log.i("Jane", response.body().getCity().getName().toString());
//                            Executors.newSingleThreadExecutor().execute(new Runnable() {
//                                @Override
//                                public void run() {
//                                    for(int i=0;i<response.body().getList().size();i++) {
//                                        WeatherEntity weatherEntity = new WeatherEntity();
//                                        weatherEntity.id = i;
//                                        weatherEntity.temp = response.body().getList().get(i).getMain().getTemp() - 273;
//                                        weatherEntity.pressure = response.body().getList().get(i).getMain().getPressure();
//                                        weatherEntity.dateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(response.body().getList().get(i).getDt() * 1000));
//                                        weatherEntity.wind = response.body().getList().get(i).getWind().getSpeed();
//                                        ((WeatherDB) database).weatherDao().insertWeather(weatherEntity);
//                                    }
//                                }
//                            });
//                            Log.i("Jane", String.valueOf(response.body().getList().size()));
//                            EditText cityName = findViewById(R.id.city);
//                            cityName.setText(response.body().getCity().getName().toString());
//                            for(int i=0;i<response.body().getList().size();i++) {
//                                Log.i("Jane cloud", String.valueOf(response.body().getList().get(i).getWeather().get(0).getMain()));
//                                Log.i("Jane data", String.valueOf(response.body().getList().get(i).getDt()));
//                                Log.i("Jane temp",   ""+(Double.valueOf(response.body().getList().get(i).getMain().getTemp().toString())-273));
//                                Log.i("Jane wind",  response.body().getList().get(i).getWind().getSpeed().toString());
//                                Log.i("Jane pressure",  response.body().getList().get(i).getMain().getPressure().toString());
//                            }
//                            Adapter adapter;
//                            adapter = new Adapter(getApplicationContext(),response.body().getList());
//                            recyclerView.setAdapter(adapter);
//                        } else Log.i("Jane", "no reponse");
//                    }
//                    @Override
//                    public void onFailure(Call<WeekWeather> call, Throwable t) {
//                        Log.i("Jane","Failure "+t);
//                    }
//                })