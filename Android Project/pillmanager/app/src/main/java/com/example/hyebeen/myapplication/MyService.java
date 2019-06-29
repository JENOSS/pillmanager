package com.example.hyebeen.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

public class MyService extends Service {

    NotificationManager Notifi_M;
    Notification Notifi;
    ServiceThread thread;

    public MyService() {
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //return super.onStartCommand(intent, flags, startId);
        Notifi_M = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        myServiceHandler handler = new myServiceHandler();
        thread = new ServiceThread(handler);
        thread.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        //super.onDestroy();
        thread.stopForever();
        thread = null; //to be fast destroy
    }

    class myServiceHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            Intent intent = new Intent(MyService.this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(MyService.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            //TODO:2번약 화면으로 가도록

            Notifi = new Notification.Builder(getApplicationContext())
                    .setContentTitle("약 먹을 시간이에요!")
                    //.setContentText("어떤 약을 몇 알 먹어야 해요~")
                    .setSmallIcon(R.drawable.medicine_icon)
                    .setTicker("알림!!!!")
                    .setContentIntent(pendingIntent)
                    .build();

            //sound
            Notifi.defaults = Notification.DEFAULT_SOUND;

            //once
            Notifi.flags = Notification.FLAG_ONLY_ALERT_ONCE;
//
            //auto alarm off
            Notifi.flags = Notification.FLAG_AUTO_CANCEL;

            Notifi_M.notify(777, Notifi);

        }
    }
}
