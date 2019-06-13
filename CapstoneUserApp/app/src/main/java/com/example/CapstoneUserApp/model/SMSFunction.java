package com.example.CapstoneUserApp.model;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SMSFunction extends Service {
    private Context context;
    private Activity activity;
    private String phoneNumber;
    private String message;
    List<MessageModel> messageList;
    List<WaitingModel> waitingList;

    //서비스 상태 파악하는 부분
    public static Intent serviceIntent = null;

    PendingIntent sentPI;

    public SMSFunction() {
        //기본생성자는 비워두기
    }

    @Override
    public IBinder onBind(Intent intent) {
        //Service 객체와 (화면단 Activity 사이에서) 통신(데이터를 주고받을) 때 사용하는 메소드
        //데이터 전달이 필요 없으면 return null;
        return null;
    }

    //정상적으로 서비스가 실행되었으면 실행한다.
    //서비스 켜지는지 확인하려고 잠깐 토스트 띄우게 해놨음
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Service", "서비스 실행합니다.");
        Toast.makeText(getApplicationContext(), "WMS Service Load Complete!", Toast.LENGTH_SHORT).show();

        messageList = new ArrayList<>();
        waitingList = new ArrayList<>();

        //Firebase 데이터 들어오는 것들에 대한 이벤트 리스너 생성
        //자동적으로 대기하고 데이터가 변화되면 onDataChange에서 변화 감지하고 SMS 전송
        final String uid = FirebaseAuth.getInstance().getUid();
        FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("smslist")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    //해당 child에 대해서 변화가 있을때마다 호출(callback)
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        //권한 확인하기 위해서
                        int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS);

                        messageList.clear(); //누적된 제거
                        waitingList.clear();
                        //child 싸그리 for문 돌려서 전부 받아오기
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            messageList.add(snapshot.getValue(MessageModel.class));
                            waitingList.add(snapshot.getValue(WaitingModel.class));
                            Log.e("count : ", messageList.size() + "");
                            Log.e("count : ", waitingList.size() + "");
                        }

                        //List의 최초값을 확인, 안비어있을때만 실행
                        //리스트 비어있는거 확인 안하면 NullPointerException 등장
                        if(!messageList.isEmpty()) {
                            message = messageList.get(0).getSms();
                            phoneNumber = messageList.get(0).getPhoneNumber();

                            if(permissionCheck == PackageManager.PERMISSION_DENIED){
                                ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.SEND_SMS},1);
                                Toast.makeText(getApplicationContext(),"권한을 허용하고 재전송해주세요",Toast.LENGTH_LONG).show();
                            } else {
                                SmsManager sms = SmsManager.getDefault();

                                // 아래 구문으로 지정된 핸드폰으로 문자 메시지를 보낸다
                                sms.sendTextMessage(phoneNumber, null, message, sentPI, null);
                                Toast.makeText(getApplicationContext(),"메세지를 감지하고 SMS를 전송합니다. ",Toast.LENGTH_LONG).show();


                                FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("smslist").removeValue();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });



    }

    //어찌 되었든 서비스는 뒤지기 마련이므로 좀비로 만들기 위한 부분
    @Override
    public void onDestroy() {
        super.onDestroy();
        serviceIntent = null;
    }

    public SMSFunction(Activity activity) {
        this.activity = activity;
    }

    public SMSFunction(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void sendSMS(String phoneNumber, String message) {

        // 권한이 허용되어 있는지 확인한다
        int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS);

        // 권한을 허용 여부를 물어봐야 SmsManager가 작동하니 if문으로 이렇게 물어봐줘야한다.
        if(permissionCheck == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.SEND_SMS},1);
            Toast.makeText(context,"권한을 허용하고 재전송해주세요",Toast.LENGTH_LONG).show();
        } else {
            SmsManager sms = SmsManager.getDefault();

            // 아래 구문으로 지정된 핸드폰으로 문자 메시지를 보낸다
            sms.sendTextMessage(phoneNumber, null, message, sentPI, null);
            Toast.makeText(context,"전송을 완료하였습니다",Toast.LENGTH_LONG).show();
        }
    }

}