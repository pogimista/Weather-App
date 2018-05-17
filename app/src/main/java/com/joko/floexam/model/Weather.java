package com.joko.floexam.model;

public class Weather {


    int id;
    String main;
    String description;
    String icon;

    public void setMain(String main) {
        this.main = main;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    public String getMain() {
        return main;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIconUrl(){
        return "http://openweathermap.org/img/w/" + icon + ".png";
    }
}
