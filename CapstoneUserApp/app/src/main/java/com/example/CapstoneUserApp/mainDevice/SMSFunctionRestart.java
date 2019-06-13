package com.example.CapstoneUserApp.mainDevice;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.example.CapstoneUserApp.model.SMSFunction;
import com.example.capstonewms.R;

public class SMSFunctionRestart extends Service {
    //죽지 않는 서비스를 위한 코드
    //Foreground를 통해 실행되지만 Android 알림창에 표시되는 문제가 있는데..
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");
        builder.setSmallIcon(R.mipmap.ic_launcher);

        //Restart를 startForeground로 실행하고
        Notification notification = builder.build();
        startForeground(9, notification);

        //SMSFunction을 실행(startService)
        Intent in = new Intent(this, SMSFunction.class);
        startService(in);

        //실행된 Restart 서비스를 stopForeground를 실행해 Restart 서비스는 종료
        stopForeground(true);
        stopSelf();

        //부팅후 실행을 위한 코드(RebootReceiver)도 있다는데 나중에 생각
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
