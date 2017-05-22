package com.example.alexander.hikebulgaria.data;

import com.example.alexander.hikebulgaria.latlang.MyLatLng;
import com.google.android.gms.maps.model.LatLng;

public class MarkerData {
    private MyLatLng coordinates;
    private String body;

    public MarkerData() {}

    public MyLatLng getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(MyLatLng coordinates) {
        this.coordinates = coordinates;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public MarkerData(MyLatLng coords, String body) {
        this.coordinates = coords;
        this.body = body;

    }
}