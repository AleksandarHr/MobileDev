package com.example.alexander.weatherreport.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.internal.RealmObjectProxy;


public class CityData extends RealmObject {

    @PrimaryKey
    private String cityID;

    private String cityName;

    public CityData() {}

    public String getCityID() {
        return cityID;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public CityData(String name) {
        this.cityName = name;
    }
}
