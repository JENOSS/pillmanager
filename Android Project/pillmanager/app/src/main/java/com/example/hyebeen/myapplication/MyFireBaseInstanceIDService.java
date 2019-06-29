package com.example.hyebeen.myapplication;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class MyFireBaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIDService";
    private static final String myURL = "";
    @Override
    public void onTokenRefresh(){
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG,"Refreshed token"+token);

        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        /**
         ** Add custom implementation, as needed
         **/

        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add("Token",token).build();

        //request
        Request request = new Request.Builder()
                .url("http://"+myURL+"/api/fcmtest")
                .post(body)
                .build();

        try{
            client.newCall(request).execute();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
