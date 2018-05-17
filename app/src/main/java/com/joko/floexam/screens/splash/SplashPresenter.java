package com.joko.floexam.screens.splash;

import com.joko.floexam.networking.Service;
import com.joko.floexam.screens.main.MainView;

import java.util.Timer;
import java.util.TimerTask;

import rx.subscriptions.CompositeSubscription;

public class SplashPresenter {

    SplashView view;

    public SplashPresenter(SplashView view) {
        this.view = view;
    }

    public void splash(){
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
               view.goToMain();
            }
        }, 2000);
    }
}
