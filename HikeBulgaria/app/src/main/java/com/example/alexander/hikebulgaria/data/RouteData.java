package com.example.alexander.hikebulgaria.data;


public class RouteData {

    private String encodedPath;
    private String description;
    private double rating;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public RouteData () {}

    public RouteData (String encodedPath) {
        this.encodedPath = encodedPath;
    }

    public String getEncodedPath() {
        return encodedPath;
    }

    public void setEncodedPath(String encodedPath) {
        this.encodedPath = encodedPath;
    }
}
