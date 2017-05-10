package com.example.alexander.weatherreport;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
    private String nameOfCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final WeatherAPI weatherAPI = retrofit.create(WeatherAPI.class);

        final TextView tvName = (TextView) findViewById(R.id.cityName);
        final TextView tvCurTemp = (TextView) findViewById(R.id.currTemp);
        final TextView tvMinTemp = (TextView) findViewById(R.id.lowestTemp);
        final TextView tvMaxTemp = (TextView) findViewById(R.id.highestTemp);

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if(bd != null)
        {
            nameOfCity = (String) bd.get("name");
            tvName.setText(nameOfCity);
        }

                Call<WeatherResult> callBuda = weatherAPI.getWeatherFor(nameOfCity, "metric", apiKey);
                callBuda.enqueue(new Callback<WeatherResult>() {
                    @Override
                    public void onResponse(Call<WeatherResult> call, Response<WeatherResult> response) {

                        tvName.setText(response.body().getCity().getName());
                        tvCurTemp.setText(response.body().getList().get(0).getMain().getTemp().toString());
                        tvMinTemp.setText(response.body().getList().get(0).getMain().getTempMin().toString());
                        tvMaxTemp.setText(response.body().getList().get(0).getMain().getTempMax().toString());
                    }

                    @Override
                    public void onFailure(Call<WeatherResult> call, Throwable t) {
                        tvName.setText(t.getLocalizedMessage());
                    }
                });
    }

}