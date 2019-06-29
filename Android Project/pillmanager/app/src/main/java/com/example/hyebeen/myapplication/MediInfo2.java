package com.example.hyebeen.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MediInfo2 extends AppCompatActivity {

    private static String IP_ADDRESS = "192.168.43.46";
    private static String TAG = "phptest";

    private static String TAG1 = "phptest_MainActivity";

    private static final String TAG_JSON="storage";
    private static final String TAG_ID = "num";
    private static final String TAG_NAME = "cnt";
    private static final String TAG_ADDRESS ="date";

    private TextView mTextViewResult;
    ArrayList<HashMap<String, String>> mArrayList;
    ListView mlistView;
    String mJsonString;




    //---------------View------------------//
    private TextView num;
    private TextView mediName;
    private TextView mediInfo;
    private ImageView imageView;

    private TextView one;
    private TextView all;
    private TextView date;

    private Button cautionButton;
    private Button donotButton;

    private Button alamSetButton;

    private Button resetButton;

    private Intent intent;
    private int buttonNum=0;
    //----------------------------------------//



    /*********************Begin of OnCreate*************************/
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.medi2_info);

        //DBHelper생성
        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "Medicine.db", null, 1);

        mArrayList = new ArrayList<>();

        GetData task1 = new GetData();
        task1.execute("http://192.168.43.46/cntjson.php");



        //-----------------ButtonNum 받아오기---------------//
        intent = getIntent();
        buttonNum = intent.getIntExtra("ButtonNum",0);

        //----------------image 띄우기----------------------//
        imgName = dbHelper.findimg(buttonNum);
        imgUrl  = "http://"+IP_ADDRESS+"/img/"+imgName;
        task = new ShowImage();
        task.execute(imgUrl);




        //-----------------findViewByld-------------------//

        num=(TextView)findViewById(R.id.num);
        mediName = (TextView) findViewById(R.id.medi2_name);
        mediInfo = (TextView) findViewById(R.id.medi2_info2);
        imageView = (ImageView) findViewById(R.id.imageView);
        one = (TextView) findViewById(R.id.eat_num2);
        all = (TextView) findViewById(R.id.total_num2);
        cautionButton = (Button) findViewById(R.id.cautionButton);
        donotButton = (Button) findViewById(R.id.donotButton);
        alamSetButton = (Button) findViewById(R.id.alamSetting);
        resetButton = (Button) findViewById(R.id.resetButton);
        date=(TextView)findViewById(R.id.lastTime);


        // Text Setting
        num.setText(Integer.toString(buttonNum));
        mediName.setText(dbHelper.findname(buttonNum));
        mediInfo.setText(dbHelper.findinfo(buttonNum));
        all.setText(Integer.toString(dbHelper.findall(buttonNum)));

        //





        //--------------------Listener--------------------//

        cautionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:SHOW CAUTION
                Intent intent = new Intent(getApplicationContext(),PopUp_info.class);
                intent.putExtra("data",dbHelper.findcaution(buttonNum));
                startActivityForResult(intent, 1);
            }
        });

        donotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:SHOW DONOT
                Intent intent = new Intent(getApplicationContext(),PopUp_info.class);
                intent.putExtra("data",dbHelper.finddonot(buttonNum));
                startActivityForResult(intent, 1);
            }
        });

        alamSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),PopUp_alamSetting.class);
                startActivityForResult(intent, 1);
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:RESET
                Intent intent = new Intent(getApplicationContext(),PopUp_reset.class);
                intent.putExtra("data",buttonNum);
                startActivityForResult(intent, 1);
            }
        });

    }/**********************END of OnCreate*************************/


    private class GetData extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(MediInfo2.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
//            mTextViewResult.setText(result);
            Log.d(TAG1, "response  - " + result);

            if (result == null){

                mTextViewResult.setText(errorString);
            }
            else {

                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG1, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG1, "InsertData: Error ", e);
                errorString = e.toString();

                return null;
            }

        }

    }


    private void showResult(){
        try {
            final DBHelper dbHelper = new DBHelper(getApplicationContext(), "Medicine.db", null, 1);
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String id = item.getString(TAG_ID);
                String name = item.getString(TAG_NAME);
                String address = item.getString(TAG_ADDRESS);

                HashMap<String,String> hashMap = new HashMap<>();

                hashMap.put(TAG_ID, id);
                hashMap.put(TAG_NAME, name);
                hashMap.put(TAG_ADDRESS, address);

                mArrayList.add(hashMap);
            }

            ListAdapter adapter = new SimpleAdapter(
                    MediInfo2.this, mArrayList, R.layout.item_list,
                    new String[]{TAG_ID,TAG_NAME, TAG_ADDRESS},
                    new int[]{R.id.textView_list_id, R.id.textView_list_name, R.id.textView_list_address}
            );

            String onee;
            onee=Integer.toString(getcnt(buttonNum)*dbHelper.findone(buttonNum));
            one.setText(onee);
            date.setText(mArrayList.get(buttonNum-1).get(TAG_ADDRESS));

            if(Integer.parseInt(one.getText().toString())>=Integer.parseInt(all.getText().toString())){
                Intent intent = new Intent(getApplicationContext(),All_eaten.class);
                startActivity(intent);
            }

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }


    public int getcnt(int num){
        for(int i =0;i<4;i++){
            if(Integer.parseInt(mArrayList.get(i).get(TAG_ID).toString())==buttonNum){
                return Integer.parseInt(mArrayList.get(i).get(TAG_NAME).toString());
            }
        }
        return 0;
    }


    /**
     * image 처리
     */

    String imgName;
    String imgUrl;

    Bitmap bmImg;
    ShowImage task;

    private class ShowImage extends AsyncTask<String, Integer,Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {

            // TODO Auto-generated method stub

            try{

                URL myFileUrl = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection)myFileUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();

                InputStream is = conn.getInputStream();

                bmImg = BitmapFactory.decodeStream(is);

            }catch(IOException e){
                e.printStackTrace();
            }

            return bmImg;
        }
        protected void onPostExecute(Bitmap img){

            if(img == null){
                Toast.makeText(getApplicationContext(),"이미지를 불러오는데 실패했습니다.",Toast.LENGTH_LONG).show();
            }else {
                imageView.setImageBitmap(bmImg);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1){
            if(resultCode==RESULT_OK)
                finish();
        }
    }

}
