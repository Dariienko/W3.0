package com.example.w30;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.w30.data.List;
import com.squareup.picasso.Picasso;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private java.util.List<List> mData;//список данных, которые будем помещать в RecyclerView
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // передаём данные в конструктор
    Adapter(Context context, java.util.List<List> data) {
        this.mInflater = LayoutInflater.from(context);
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
        List animal = mData.get(position);
        String clouds = animal.getWeather().get(0).getMain();
        Log.i("Clouds", clouds);
        if(clouds.equals("Rain")){
            Picasso.get().load("https://i1.sndcdn.com/artworks-000024885338-5w36ki-t500x500.jpg").into(holder.imageView);
        }else if(clouds.equals("Snow")){
            Picasso.get().load("https://upload.wikimedia.org/wikipedia/commons/thumb/d/d6/Field-with-snow-champ-enneige.jpg/640px-Field-with-snow-champ-enneige.jpg").into(holder.imageView);
        }else {
            Picasso.get().load("https://static.wixstatic.com/media/2cd43b_82c095ac2f7446839d199a98506b87ae~mv2_d_1440_1440_s_2.png/v1/fill/w_414,h_412,q_90/2cd43b_82c095ac2f7446839d199a98506b87ae~mv2_d_1440_1440_s_2.png").into(holder.imageView);
        }
        String date = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date(animal.getDt() * 1000));
        holder.myData.setText("Data: " + date);
        holder.myTextWind.setText("Wind - " + animal.getWind().getSpeed().toString());
        holder.myTextTemp.setText("Temp - "+ Double.valueOf(animal.getMain().getTemp()-273).toString());
        holder.myPressure.setText("Pressure - " + animal.getMain().getPressure().toString());
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
    List getItem(int id) {
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

