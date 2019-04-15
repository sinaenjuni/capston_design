package com.example.y2k43.firebaseexample;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class LoginActivity extends AppCompatActivity {
    private Button loggin;
    private Button singup;

    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        String splash_background = mFirebaseRemoteConfig.getString(getString(R.string.re_color));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor(splash_background));
        }


        loggin = (Button) findViewById(R.id.login_Button_login);
        singup = (Button) findViewById(R.id.login_Button_resister);

        loggin.setBackground(getResources().getDrawable(R.drawable.button_background));
        singup.setBackground(getResources().getDrawable(R.drawable.button_background));

        loggin.setBackgroundColor(Color.parseColor(splash_background));
        singup.setBackgroundColor(Color.parseColor(splash_background));

        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this, SignupActivity.class));

            }
        });


    }
}
