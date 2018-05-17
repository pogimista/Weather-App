package com.joko.floexam.screens.main;

import com.joko.floexam.model.WeatherResponse;

/**
 * Created by john.mista on 5/16/18.
 */
public interface MainView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getWeatherInfoSuccess(WeatherResponse response);
}
