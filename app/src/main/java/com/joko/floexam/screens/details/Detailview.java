package com.joko.floexam.screens.details;

import com.joko.floexam.model.WeatherResponse;

public interface Detailview {

    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getWeatherInfoSuccess(WeatherResponse response);
}
