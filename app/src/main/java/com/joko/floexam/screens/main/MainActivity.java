package com.joko.floexam.screens.main;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.joko.floexam.BaseApp;
import com.joko.floexam.R;
import com.joko.floexam.model.WeatherResponse;
import com.joko.floexam.networking.Service;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseApp implements MainView {

    @Inject
    public Service service;

    FusedLocationProviderClient client;
    MainPresenter presenter;
    public List<WeatherResponse> weatherResponses;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rel_tap_to_refresh) RelativeLayout rlTap;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDeps().inject(this);

        renderView();
        init();

        presenter = new MainPresenter(service, this);
    }

    public void renderView() {
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Weather Information");

        rlTap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
            }
        });
    }

    public void init() {
        weatherResponses = new ArrayList<>();
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
        rlTap.setVisibility(View.VISIBLE);
        showAlertDialog(getResources().getString(R.string.error_title),
                getResources().getString(R.string.error_generic));
    }

    @Override
    public void getWeatherInfoSuccess(WeatherResponse response) {
        weatherResponses.add(response);
        if (weatherResponses.size() == 4) {
            rlTap.setVisibility(View.GONE);
            hideLoading();
            initFragment();
        }
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MainFragment fragment = new MainFragment();
        fragmentTransaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private void fetchWeatherData() {
        presenter.getWeatherInfo("LONDON");
        presenter.getWeatherInfo("PRAGUE");
        presenter.getWeatherInfo("SAN FRANCISCO");
    }

    private void fetchWeatherDataWithLocation(Location location) {
        presenter.getWeatherInfo("LONDON");
        presenter.getWeatherInfo("PRAGUE");
        presenter.getWeatherInfo("SAN FRANCISCO");
        presenter.getWeatherInfo(location);
    }

    private boolean checkLocationIfEnabled(){
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public void getLocation() {
        if (!checkLocationIfEnabled()){
            showLocationEnableDialog();
            return;
        }

        client = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Dexter.withActivity(this)
                    .withPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                    .withListener(new PermissionListener() {
                        @Override public void onPermissionGranted(PermissionGrantedResponse response) {
                            getLocation();
                        }
                        @Override public void onPermissionDenied(PermissionDeniedResponse response) {
                            fetchWeatherData();
                        }
                        @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                            token.continuePermissionRequest();
                        }
                    }).check();
        }else {
            showLoading("Getting location...");
            client.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    hideLoading();
                    if (location!=null){
                        weatherResponses.clear();
                        fetchWeatherDataWithLocation(location);
                    }else {
                        rlTap.setVisibility(View.VISIBLE);
                    }
                }
            })
            .addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    hideLoading();
                    rlTap.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    private void showLocationEnableDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Please enable your location setting");
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(myIntent);
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLocation();
    }
}
