package com.example.CapstoneUserApp;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.example.CapstoneUserApp.mainDevice.MainDeviceMain;
import com.example.capstonewms.R;
import com.example.CapstoneUserApp.login.LoginView;

interface ISplashView {

    void setBackground(String color);
    void displayMessage();
//    void displayMessage(boolean splash_caps, String splash_message);
}


public class SplashView extends AppCompatActivity implements ISplashView{

    private SplashPresenter presenter;

    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        linearLayout = (LinearLayout)findViewById(R.id.splash_linearlayout_background);

        presenter = new SplashPresenter();
        presenter.attachView(this);
        presenter.loadRemoteConfig();
        presenter.loadBackGroundColor();
        presenter.displayMessage();


    }


    @Override
    public void setBackground(String color) {
        linearLayout.setBackgroundColor(Color.parseColor(color));
    }

    @Override
    public void displayMessage() {
        String splash_message = presenter.getString("splash_message");
        boolean splash_caps = presenter.getBoolean("splash_message_caps");

        if (splash_caps) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SplashView.this);
            builder.setMessage(splash_message)
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.create().show();
        } else {

            startActivity(new Intent(SplashView.this, MainDeviceMain.class));
            finish();
        }
    }


    @Override
    public void onBackPressed() {
        return;
    }



}
