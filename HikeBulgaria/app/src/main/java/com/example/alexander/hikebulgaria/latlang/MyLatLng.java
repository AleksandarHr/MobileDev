package com.example.alexander.hikebulgaria.latlang;

/**
 * Created by Alexander on 22.5.2017.
 */

public class MyLatLng {

    private double latitude;
    private double longitude;

    public MyLatLng () {}

    public MyLatLng(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
