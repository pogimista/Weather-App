package com.joko.floexam.networking;


import com.joko.floexam.model.WeatherResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by john.mista on 5/16/18.
 */
public interface NetworkService {

    @GET("weather")
    Observable<WeatherResponse> getWeather(@Query("q") String place, @Query("appid") String appid);

    @GET("weather")
    Observable<WeatherResponse> getWeatherByCoordinates(@Query("lat") Double latitude,@Query("lon") Double longitude, @Query("appid") String appid);

}
