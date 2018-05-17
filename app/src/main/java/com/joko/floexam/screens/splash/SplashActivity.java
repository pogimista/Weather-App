package com.joko.floexam.screens.splash;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.joko.floexam.BaseApp;
import com.joko.floexam.BuildConfig;
import com.joko.floexam.R;
import com.joko.floexam.screens.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseApp implements SplashView {

    SplashPresenter presenter;

    @BindView(R.id.txt_flavor) TextView txtFlavor;
    @BindView(R.id.txt_version) TextView txtVersion;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        renderView();
        presenter = new SplashPresenter(this);
        presenter.splash();
    }

    public  void renderView(){
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        txtFlavor.setText(BuildConfig.TITLE);
        txtVersion.setText("v." + BuildConfig.VERSION_NAME);
    }

    @Override
    public void goToMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
