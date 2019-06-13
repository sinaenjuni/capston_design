package com.example.capstonewms;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;

public class SplashPresenter {

    private ISplashView view;
    private ISplashRepositoty repositoty;

    public void attachView(ISplashView view){
        this.view = view;
        this.repositoty = SplashRepositoty.getInstance();
    }


    public void loadRemoteConfig() {
        repositoty.firebaseRemoteConfigInit(view);
    }

    public void displayMessage() {

//        view.displayMessage(repositoty.getBoolean("splash_message_caps"),
//                repositoty.getString("splash_message"));

    }

    public void loadBackGroundColor() {
        view.setBackground(repositoty.getString("splash_background"));
    }

    public String getString(String string){
        return repositoty.getString(string);
    }

    public boolean getBoolean(String string){
        return repositoty.getBoolean(string);
    }
}
