package com.example.hyebeen.myapplication;

import android.os.Handler;

public class ServiceThread extends Thread {
    Handler handler;
    boolean isRun = true;

    public ServiceThread(Handler handler) {
        this.handler = handler;
    }

    public void stopForever() {
        synchronized (this) {
            this.isRun = false;
        }
    }

    public void run() {
        //TODO: Alarm !!!
        while(isRun){
            handler.sendEmptyMessage(0);

            stopForever();
//            try{
//                Thread.sleep(5000); //5초씩 쉰다.
//            }catch (Exception e) {};
        }
    }
}
