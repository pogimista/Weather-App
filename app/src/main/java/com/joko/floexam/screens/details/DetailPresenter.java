package com.joko.floexam.screens.details;

import com.joko.floexam.model.WeatherResponse;
import com.joko.floexam.networking.NetworkError;
import com.joko.floexam.networking.Service;
import com.joko.floexam.screens.main.MainView;
import com.joko.floexam.screens.splash.SplashView;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class DetailPresenter {

    Service service;
    CompositeSubscription subscriptions;
    Detailview view;

    public DetailPresenter(Service service, Detailview view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void getWeatherInfo(String PLACE) {
        view.showWait();

        Subscription subscription = service.getWeather(new Service.getWeatherCallback(){
            @Override
            public void onSuccess(WeatherResponse response) {
                view.removeWait();
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

    public void onStop() {
        subscriptions.unsubscribe();
    }
}
