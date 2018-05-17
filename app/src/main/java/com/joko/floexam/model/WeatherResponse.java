package com.joko.floexam.model;

import java.util.List;

public class WeatherResponse {


    Coordinates coord;
    List<Weather> weather;
    Temperature main;
    String name;
    String summary;

    public Coordinates getCoord() {
        return coord;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public String getName() {
        return name;
    }

    public Temperature getMain() {
        return main;
    }

    public void setCoord(Coordinates coord) {
        this.coord = coord;
    }

    public void setMain(Temperature main) {
        this.main = main;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public String getWeatherSummary(){
        String summary = "";
        for (Weather w: weather){
            summary = summary + w.getDescription();
        }
        return summary;
    }
}
