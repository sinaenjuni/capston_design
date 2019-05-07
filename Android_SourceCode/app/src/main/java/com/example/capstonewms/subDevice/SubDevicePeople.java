package com.example.capstonewms.subDevice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstonewms.R;
import com.example.capstonewms.model.WaitingModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SubDevicePeople extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewEnter;
    private TextView textViewPeople;
    private TextView textViewClear;
    private TextView textViewWaitPeople;
    private List<WaitingModel> waitingList;


    private String[] textViewNumberString = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private TextView[] textViewNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_device_people);

        textViewClear = (TextView) findViewById(R.id.subDeviceMain_TextView_clear);
        textViewClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempStr = textViewWaitPeople.getText().toString();
                int length = tempStr.length();
                if (!tempStr.equals("")) {
                    if(textViewWaitPeople.getText().length() == 1) {
                        tempStr = "0";
                    } else {
                        tempStr = tempStr.substring(0, length - 1);
                    }
                    textViewWaitPeople.setText(tempStr);
                } else {
                    textViewWaitPeople.setText("0");
                    Toast.makeText(SubDevicePeople.this, "그만눌러", Toast.LENGTH_LONG).show();
                }
                //textViewPhone.setText(textViewPhone.getText().toString().substring(0, textViewPeople.getText().length()));
            }
        });

        textViewNumber = new TextView[textViewNumberString.length];
        for (int i = 0; i < textViewNumberString.length; i++) {
            textViewNumber[i] = (TextView) findViewById(R.id.subDeviceMain_TextView_number0 + i);
            textViewNumber[i].setOnClickListener(this);
        }

        waitingList = new ArrayList<>();

        textViewWaitPeople = (TextView)findViewById(R.id.subDeviceMain_TextView_waitPeople);
        textViewPeople = (TextView)findViewById(R.id.subDeviceMain_TextView_People);
        textViewEnter = (TextView) findViewById(R.id.subDeviceMain_TextView_enter);
        textViewEnter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String uid = FirebaseAuth.getInstance().getUid();
                FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("waitinglist")
                        .addValueEventListener(new ValueEventListener() {
                               @Override
                               public void onDataChange(@NonNull DataSnapshot dataSnapshot) { //서버에서 넘어오는 데이터
                                   waitingList.clear(); //누적 제거거
                                   for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                       waitingList.add(snapshot.getValue(WaitingModel.class));
                                       Log.e("count : ", waitingList.size() + "");
                                   }
                               }

                               @Override
                               public void onCancelled(@NonNull DatabaseError databaseError) {

                               }
                           });

                Toast.makeText(SubDevicePeople.this, "추가함", Toast.LENGTH_LONG).show();

                Intent intent = getIntent();
                String phone = intent.getStringExtra("phone");
                startActivity(new Intent(SubDevicePeople.this, SubDeviceMain.class));


                FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("waitinglist")
                        .child(waitingList.size() + "")
                        .setValue(new WaitingModel(waitingList.toString() + "", phone,textViewWaitPeople.getText().toString()));
            }
        });

        String uid = FirebaseAuth.getInstance().getUid();
        FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("waitinglist")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //서버에서 넘어오는 데이터
                        waitingList.clear(); //누적 제거거
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            waitingList.add(snapshot.getValue(WaitingModel.class));
                            Log.e("count : ", waitingList.size() + "");
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textViewPeople.setText(waitingList.size()+"");
                            }
                        });
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        int selButton = v.getId();
        String text = textViewWaitPeople.getText().toString();

        switch (selButton) {
            case R.id.subDeviceMain_TextView_number0:
                textViewWaitPeople.setText(text + "0");
                break;
            case R.id.subDeviceMain_TextView_number1:
                textViewWaitPeople.setText(text + "1");
                break;
            case R.id.subDeviceMain_TextView_number2:
                textViewWaitPeople.setText(text + "2");
                break;
            case R.id.subDeviceMain_TextView_number3:
                textViewWaitPeople.setText(text + "3");
                break;
            case R.id.subDeviceMain_TextView_number4:
                textViewWaitPeople.setText(text + "4");
                break;
            case R.id.subDeviceMain_TextView_number5:
                textViewWaitPeople.setText(text + "5");
                break;
            case R.id.subDeviceMain_TextView_number6:
                textViewWaitPeople.setText(text + "6");
                break;
            case R.id.subDeviceMain_TextView_number7:
                textViewWaitPeople.setText(text + "7");
                break;
            case R.id.subDeviceMain_TextView_number8:
                textViewWaitPeople.setText(text + "8");
                break;
            case R.id.subDeviceMain_TextView_number9:
                textViewWaitPeople.setText(text + "9");
                break;
        }

        if(Integer.parseInt(text) > 100) {
            Toast.makeText(SubDevicePeople.this, "100명은 넘길수 없다.", Toast.LENGTH_LONG).show();
            textViewWaitPeople.setText("100");
        }


    }
}
