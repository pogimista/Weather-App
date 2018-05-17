package com.joko.floexam.screens.details;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.joko.floexam.BaseApp;
import com.joko.floexam.R;
import com.joko.floexam.model.Weather;
import com.joko.floexam.model.WeatherResponse;
import com.joko.floexam.networking.Service;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends BaseApp implements Detailview {

    @Inject
    public Service service;
    DetailPresenter presenter;
    WeatherResponse weatherResponse;
    FirebaseAnalytics firebaseAnalytics;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.txt_detail_place) TextView txtPlace;
    @BindView(R.id.text_detail_weather) TextView txtWeather;
    @BindView(R.id.text_detail_desc) TextView txtDescription;
    @BindView(R.id.txt_detail_humidity) TextView txtHumidity;
    @BindView(R.id.txt_detail_temperature) TextView txtTemperature;
    @BindView(R.id.txt_detail_pressure) TextView txtPressure;
    @BindView(R.id.iv_detail_icon) ImageView ivWeatherIcon;
    @BindView(R.id.fab_refresh) FloatingActionButton fabRefresh;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDeps().inject(this);
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        renderView();
        presenter = new DetailPresenter(service,this);
        EventBus.getDefault().register(this);
    }

    public  void renderView(){
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        fabRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.getWeatherInfo(weatherResponse.getName());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void handleEventBus(WeatherResponse response){
        weatherResponse = response;
        initData();

        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
    }

    private void initData(){
        txtPlace.setText(weatherResponse.getName());
        txtHumidity.setText(String.valueOf(weatherResponse.getMain().getHumidity()));
        txtTemperature.setText(weatherResponse.getMain().toString());
        txtPressure.setText(String.valueOf(weatherResponse.getMain().getPressure()));

        Weather weather = weatherResponse.getWeather().get(0);
        txtWeather.setText(weather.getMain());
        txtDescription.setText(weather.getDescription());
        Glide.with(this).load(weather.getIconUrl()).into(ivWeatherIcon);
    }

    @Override
    public void showWait() {
        showLoading(getResources().getString(R.string.loading_get_info_list));
    }

    @Override
    public void removeWait() {
        hideLoading();
    }

    @Override
    public void onFailure(String appErrorMessage) {
        showAlertDialog(getResources().getString(R.string.error_title),appErrorMessage);
    }

    @Override
    public void getWeatherInfoSuccess(WeatherResponse response) {
        weatherResponse = response;
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Bundle params = new Bundle();
        params.putString("location", weatherResponse.getName());
        params.putString("weather", weatherResponse.getWeatherSummary());
        firebaseAnalytics.logEvent("weatherLocation", params);
    }
}
