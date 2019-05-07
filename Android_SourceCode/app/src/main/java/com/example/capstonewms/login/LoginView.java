package com.example.capstonewms.login;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.capstonewms.R;
import com.example.capstonewms.deviceMenu.DeviceMenu;
import com.example.capstonewms.sign.SignActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class LoginView extends AppCompatActivity {
    private EditText id;
    private EditText password;
    private FirebaseAuth mFirevFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

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

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        mFirevFirebaseAuth = FirebaseAuth.getInstance();

        //강제 로그아웃
//        mFirevFirebaseAuth.signOut();


        id = (EditText) findViewById(R.id.login_EditText_id);
        password = (EditText)findViewById(R.id.login_EditText_password);

        loggin = (Button) findViewById(R.id.login_Button_login);
        singup = (Button) findViewById(R.id.login_Button_resister);

        loggin.setBackground(getResources().getDrawable(R.drawable.button_background));
        singup.setBackground(getResources().getDrawable(R.drawable.button_background));

        loggin.setBackgroundColor(Color.parseColor(splash_background));
        loggin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginEvent();
            }
        });

        singup.setBackgroundColor(Color.parseColor(splash_background));
        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginView.this, SignActivity.class));

            }
        });

        //로그인의 상태가 변하면 호출되는 리스너
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //로그인
                    Intent intent = new Intent(LoginView.this, DeviceMenu.class);
                    startActivity(intent);
                    finish();
                }else {
                    //로그아웃

                }
            }
        };

    }

    void loginEvent(){
        mFirevFirebaseAuth.signInWithEmailAndPassword(id.getText().toString().trim(), password.getText().toString().trim())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            //로그인 실패한 부분
                            Toast.makeText(LoginView.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirevFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFirevFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }
}
