package com.example.alexander.weatherreport;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.alexander.weatherreport.data.WeatherResult;
import com.example.alexander.weatherreport.network.WeatherAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailsActivity extends AppCompatActivity {

    private final String apiKey = "3ab890e368d480238f59d88cbad17e9c";
    private final String baseURL = "http://api.openweathermap.org/data/2.5/";
    private final String baseIcon = "http://openweathermap.org/img/w/";
    private String nameOfCity;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        context = this;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final WeatherAPI weatherAPI = retrofit.create(WeatherAPI.class);

        final TextView tvName = (TextView) findViewById(R.id.cityName);
        final TextView tvCurTemp = (TextView) findViewById(R.id.currTemp);
        final TextView tvMinTemp = (TextView) findViewById(R.id.lowestTemp);
        final TextView tvMaxTemp = (TextView) findViewById(R.id.highestTemp);
        final ImageView imageView = (ImageView) findViewById(R.id.weatherLogo);

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if (bd != null) {
            nameOfCity = (String) bd.get("name");
        }


        Call<WeatherResult> callBuda = weatherAPI.getWeatherFor(nameOfCity, "metric", apiKey);
        callBuda.enqueue(new Callback<WeatherResult>() {
            @Override
            public void onResponse(Call<WeatherResult> call, Response<WeatherResult> response) {

                Glide.with(context).load(baseIcon +
                        response.body().getList().get(0).getWeather().get(0).getIcon() +
                        ".png").into(imageView);

                tvName.setText(nameOfCity);
                tvCurTemp.setText(tvCurTemp.getText() +
                        response.body().getList().get(0).getMain().getTemp().toString() +
                        "C");
                tvMinTemp.setText(tvMinTemp.getText() +
                        response.body().getList().get(0).getMain().getTempMin().toString() +
                        "C");
                tvMaxTemp.setText(tvMaxTemp.getText() +
                        response.body().getList().get(0).getMain().getTempMax().toString() +
                        "C");
            }

            @Override
            public void onFailure(Call<WeatherResult> call, Throwable t) {
                tvName.setText(t.getLocalizedMessage());
            }
        });
    }

}