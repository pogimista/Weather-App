package com.joko.floexam.model;

public class Coordinates {

    double lon;
    double lat;

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }


    public String toString(){
        return lon + "," + lat;
    }
}
