package com.joko.floexam.screens.main;

import android.location.Location;

import com.joko.floexam.model.WeatherResponse;
import com.joko.floexam.networking.NetworkError;
import com.joko.floexam.networking.Service;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by john.mista on 5/16/18.
 */
public class MainPresenter {
    private final Service service;
    private final MainView view;
    private CompositeSubscription subscriptions;

    public MainPresenter(Service service, MainView view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }


    public void getWeatherInfo(String PLACE) {
        view.showWait();

        Subscription subscription = service.getWeather(new Service.getWeatherCallback(){
            @Override
            public void onSuccess(WeatherResponse response) {
                view.getWeatherInfoSuccess(response);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getAppErrorMessage());
            }
        },PLACE);


        subscriptions.add(subscription);
    }

    public void getWeatherInfo(Location location) {
        view.showWait();

        Subscription subscription = service.getWeatherByLocation(new Service.getWeatherCallback(){
            @Override
            public void onSuccess(WeatherResponse response) {
                view.getWeatherInfoSuccess(response);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getAppErrorMessage());
            }
        },location);


        subscriptions.add(subscription);
    }

    public void onStop() {
        subscriptions.unsubscribe();
    }

}
