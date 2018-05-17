package com.joko.floexam.model;

import java.text.DecimalFormat;

public class Temperature {

    double temp;
    double pressure;
    double humidity;

    public double getHumidity() {
        return humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public double getTemp() {
        return temp;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public String toString(){
        DecimalFormat f = new DecimalFormat("##.00");
        return "" + f.format((temp - 270)) + " C";
    }
}
