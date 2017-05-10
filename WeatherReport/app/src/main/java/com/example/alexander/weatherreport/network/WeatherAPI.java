package com.example.alexander.weatherreport.network;

import com.example.alexander.weatherreport.data.WeatherResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface WeatherAPI {
    Call<WeatherResult> getWeatherFor(@Query("q") String city, @Query("units") String units,
                                      @Query("appid") String appid);
}
