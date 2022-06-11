package com.example.w30;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

public class Adapter1 extends RecyclerView.Adapter<Adapter1.ViewHolder> {

    private java.util.List<WeatherEntity> mData;//список данных, которые будем помещать в RecyclerView
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // передаём данные в конструктор

    public Adapter1(Context applicationContext, java.util.List<WeatherEntity> data) {
        this.mInflater = LayoutInflater.from(applicationContext);
        this.mData = data;
    }


    // “создает(надувает)” строку(пункт) RecyclerView из xml файла
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layoutstringitem, parent, false);
        return new ViewHolder(view);
    }

    // заполняет каждую строк RecyclerView данными
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WeatherEntity animal = mData.get(position);
//        String clouds = animal.getWeather().get(0).getMain();
//        Log.i("Clouds", clouds);
//        if(clouds.equals("Rain")){
//            Picasso.get().load("https://i1.sndcdn.com/artworks-000024885338-5w36ki-t500x500.jpg").into(holder.imageView);
//        }else if(clouds.equals("Snow")){
//            Picasso.get().load("https://upload.wikimedia.org/wikipedia/commons/thumb/d/d6/Field-with-snow-champ-enneige.jpg/640px-Field-with-snow-champ-enneige.jpg").into(holder.imageView);
//        }else {
//            Picasso.get().load("https://static.wixstatic.com/media/2cd43b_82c095ac2f7446839d199a98506b87ae~mv2_d_1440_1440_s_2.png/v1/fill/w_414,h_412,q_90/2cd43b_82c095ac2f7446839d199a98506b87ae~mv2_d_1440_1440_s_2.png").into(holder.imageView);
//        }
        holder.myData.setText("Data: " + animal.dateTime);
        holder.myTextWind.setText("Wind - " + animal.wind);
        holder.myTextTemp.setText("Temp - "+ Double.valueOf(animal.temp));
        holder.myPressure.setText("Pressure - " + animal.pressure);
    }

    // общее количество строк
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // сохраняет и реиспользует view компоненты, когда строка прокручивается(уходит с экрана)
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        EditText myTextWind;
        EditText myTextTemp;
        EditText myData;
        EditText myPressure;


        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            myPressure = itemView.findViewById(R.id.pressure);
            myTextWind = itemView.findViewById(R.id.wind);
            myTextTemp = itemView.findViewById(R.id.temp);
            myData = itemView.findViewById(R.id.data);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // получение данных из строки RecyclerView, по которой щелкнули
    WeatherEntity getItem(int id) {
        return mData.get(id);
    }

    // добавление возможности перехата нажатия на кнопку
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    //  Activity будет реализовывать этот метод, щелчек по элементу
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

