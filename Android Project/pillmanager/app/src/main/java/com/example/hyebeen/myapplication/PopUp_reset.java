package com.example.hyebeen.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class PopUp_reset extends Activity {

    private int buttonNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pop_up_reset_check);


        //intent로 버튼 번호 받아오기
        Intent intent = getIntent();
        buttonNum = intent.getIntExtra("data",0);

    }

    //No 버튼 클릭
    public void mOnClose(View v){
        finish();
    }
    //Yes 버튼 클릭
    public void resetButton(View v) {
        //DBHelper생성
        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "Medicine.db", null, 1);
        dbHelper.delete(buttonNum);

        //데이터 전달하기
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);

        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
}
