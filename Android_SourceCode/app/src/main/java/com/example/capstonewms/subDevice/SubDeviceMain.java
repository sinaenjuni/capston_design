package com.example.capstonewms.subDevice;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.capstonewms.R;
import com.example.capstonewms.model.WaitingModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class SubDeviceMain extends AppCompatActivity {

    private Button submitButton;
    private EditText personText;
    private EditText phoneText;

    String uid = FirebaseAuth.getInstance().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_device_main);

        submitButton = findViewById(R.id.submitButton);
        personText = findViewById(R.id.personText);
        phoneText = findViewById(R.id.phoneText);


    }

}

