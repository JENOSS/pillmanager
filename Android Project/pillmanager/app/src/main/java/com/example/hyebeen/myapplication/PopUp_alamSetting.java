package com.example.hyebeen.myapplication;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class PopUp_alamSetting  extends Activity implements DatePicker.OnDateChangedListener,TimePicker.OnTimeChangedListener{


    // 알람 메니저
    private AlarmManager mManager;

    // 설정 일시
    private GregorianCalendar mCalendar;

    //일자 설정 클래스
    private DatePicker mDate;

    //시작 설정 클래스
    private TimePicker mTime;

    //통지 관련 맴버 변수
    private NotificationManager mNotification;

    //버튼
    private Button setButton;
    private Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pop_up_alamset);

        //통지 매니저를 취득
        mNotification = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        //알람 매니저를 취득
        mManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        //현재 시각을 취득
        mCalendar = new GregorianCalendar();

        //셋 버튼, 리셋버튼의 리스너를 등록
        setButton = (Button)findViewById(R.id.set);
        setButton.setOnClickListener (new View.OnClickListener() {
            public void onClick (View v) {
                setAlarm();
                Toast.makeText(getApplicationContext(), "설정완료", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        resetButton = (Button)findViewById(R.id.reset);
        resetButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                resetAlarm();
                finish();
            }
        });
        //일시 설정 클래스로 현재 시각을 설정

        mDate = (DatePicker)findViewById(R.id.date_picker);
        mDate.init (mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH), this);

        mTime = (TimePicker)findViewById(R.id.time_picker);
        mTime.setCurrentHour(mCalendar.get(Calendar.HOUR_OF_DAY));

        mTime.setCurrentMinute(mCalendar.get(Calendar.MINUTE));
        mTime.setOnTimeChangedListener(this);


    }

    //알람 설정
    private void setAlarm() {
        mManager.set(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), pendingIntent());
    }

    //알람 해제
    private void resetAlarm() {
        mManager.cancel(pendingIntent());
    }

    //일자 설정 클래스의 상태변화 리스너
    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        mCalendar.set (year, monthOfYear, dayOfMonth, mTime.getCurrentHour(), mTime.getCurrentMinute());
    }

    //시각 설정 클래스의 상태변화 리스너
    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        mCalendar.set (mDate.getYear(), mDate.getMonth(), mDate.getDayOfMonth(), hourOfDay, minute);
    }



    /**
     * 알람의 설정 시각에 발생하는 작업
     */
    //Alarm Request Code
    private static final int ALARM_REQUEST_CODE = 133;

    private PendingIntent pendingIntent() {
        Intent alarmIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), ALARM_REQUEST_CODE, alarmIntent, 0);
        return pendingIntent;
    }

    /**
     *
     */


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            return false;
        }
        return true;
    }

}
