package com.example.capstonewms.model;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
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

import java.util.List;

public class SMSFunction extends Service {

    private Context context;
    private Activity activity;
    List<MessageModel> messageList;

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

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Service", "서비스 실행합니다.");
        Toast.makeText(getApplicationContext(), "서비스 실행!", Toast.LENGTH_SHORT).show();

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
