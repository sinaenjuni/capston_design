package com.example.capstonewms;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

interface ISplashRepositoty{

    void firebaseRemoteConfigInit(ISplashView view);
    String getString(String string);
    Boolean getBoolean(String string);
}

//interface CallEventListener{ void onReceivedEvent();}


public class SplashRepositoty implements ISplashRepositoty{

    private static SplashRepositoty instance;

    private FirebaseRemoteConfig mFirebaseRemoteConfig;

//    private CallEventListener mCallEventListener;

    public static SplashRepositoty getInstance(){

        if(instance == null){
            instance = new SplashRepositoty();
        }

        return instance;
    }

    public SplashRepositoty() {

    }

//    public void setOnCallbackReceiver(CallEventListener eventListener){
//        mCallEventListener = eventListener;
//    }

    @Override
    public void firebaseRemoteConfigInit(final ISplashView view) {
        final ISplashView iview = view;
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);

        mFirebaseRemoteConfig.setDefaults(R.xml.default_config);

        mFirebaseRemoteConfig.fetch(0)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

//                        displayMessage();
                            Log.e("remoteConfig", "연결");

                            // After config data is successfully fetched, it must be activated before newly fetched
                            // values are returned.
                            mFirebaseRemoteConfig.activateFetched();
                        } else {

                        }
//                        mCallEventListener.onReceivedEvent();

                        iview.displayMessage();
                    }
                });

    }

    @Override
    public String getString(String string) {
        return mFirebaseRemoteConfig.getString(string);
    }

    @Override
    public Boolean getBoolean(String string) {
        return mFirebaseRemoteConfig.getBoolean(string);
    }
}
