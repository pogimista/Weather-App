package com.joko.floexam.networking;


import android.location.Location;

import com.joko.floexam.BuildConfig;
import com.joko.floexam.model.WeatherResponse;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by john.mista on 5/16/18.
 */
public class Service {

    private final NetworkService networkService;

    public Service(NetworkService networkService) {
        this.networkService = networkService;
    }

    public Subscription getWeather(final getWeatherCallback callback,String place) {

        return networkService.getWeather(place, BuildConfig.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends WeatherResponse>>() {
                    @Override
                    public Observable<? extends WeatherResponse> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<WeatherResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(WeatherResponse response) {
                        callback.onSuccess(response);

                    }
                });
    }
    public Subscription getWeatherByLocation(final getWeatherCallback callback, Location location) {

        return networkService.getWeatherByCoordinates(location.getLatitude()
                ,location.getLongitude(), BuildConfig.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends WeatherResponse>>() {
                    @Override
                    public Observable<? extends WeatherResponse> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<WeatherResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(WeatherResponse response) {
                        callback.onSuccess(response);

                    }
                });
    }
    public interface getWeatherCallback{
        void onSuccess(WeatherResponse response);

        void onError(NetworkError networkError);
    }


}
