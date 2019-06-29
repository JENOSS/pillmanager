package com.example.hyebeen.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    // DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // DB를 새로 생성할 때 호출되는 함수
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 새로운 테이블 생성
        /* 이름은 Medicine, 자동으로 값이 증가하는 _id 정수형 기본키 컬럼과
        item 문자열 컬럼, price 정수형 컬럼, create_at 문자열 컬럼으로 구성된 테이블을 생성. */

        db.execSQL("CREATE TABLE Medicine (_id INTEGER PRIMARY KEY AUTOINCREMENT, num INTEGER, name TEXT, info TEXT, caution TEXT, donot TEXT, allnum INTEGER, one INTEGER, cnt INTEGER, img TEXT);");
    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(int num, String name, String info, String caution, String donot, int allnum, int one, int cnt,String img) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        cnt=allnum/one;
        db.execSQL("INSERT INTO Medicine VALUES(null," + num + ", '" + name + "', '" + info + "', '" + caution + "', '" + donot + "', " + allnum + ", " + one + ", " + cnt + ", '" + img + "');");
        db.close();
    }

    public void update(String item, int price) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행의 가격 정보 수정
        db.execSQL("UPDATE Medicine SET price=" + price + " WHERE item='" + item + "';");
        db.close();
    }

    public void delete(int num) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        Log.d("qwe",""+num);
        db.execSQL("DELETE FROM Medicine WHERE num = "+num+";");
        db.close();
    }

    public void drop(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE Medicine;");
    }

    public String getResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM Medicine", null);
        while (cursor.moveToNext()) {
            result += cursor.getString(0)
                    + " : "
                    + cursor.getInt(1)
                    + " : "
                    + cursor.getString(2)
                    + " : "
                    + cursor.getString(3)
                    + " : "
                    + cursor.getString(4)
                    + " : "
                    + cursor.getString(5)
                    + " : "
                    + cursor.getInt(6)
                    + " : "
                    + cursor.getInt(7)
                    + " : "
                    + cursor.getInt(8)
                    + " : "
                    + cursor.getString(9)+"\n";

        }

        return result;
    }
    public boolean isExist(int buttonNum) {
        //약이 등록되어 있는지 확인
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Medicine", null);


        while (cursor.moveToNext()){
            if(cursor.getInt(1)==buttonNum){
                return true;
            }
        }
        return false;
    }

    public String findinfo(int num){
        //info정보를 DB에서 불러오기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        Cursor cursor = db.rawQuery("SELECT * FROM Medicine", null);
        while (cursor.moveToNext()){
            if(cursor.getInt(1)==num)
                return cursor.getString(3);
        }
        return "찾을 수 없습니다.";
    }

    public String findcaution(int num){
        //caution정보를 DB에서 불러오기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        Cursor cursor = db.rawQuery("SELECT * FROM Medicine", null);
        while (cursor.moveToNext()){
            if(cursor.getInt(1)==num)
                return cursor.getString(4);
        }
        return "찾을 수 없습니다.";
    }

    public String finddonot(int num){
        //donot정보를 DB에서 불러오기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        Cursor cursor = db.rawQuery("SELECT * FROM Medicine", null);
        while (cursor.moveToNext()){
            if(cursor.getInt(1)==num)
                return cursor.getString(5);
        }
        return "찾을 수 없습니다.";
    }

    public String findname(int num){
        //약의 name정보를 DB에서 불러오기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        Cursor cursor = db.rawQuery("SELECT * FROM Medicine", null);
        while (cursor.moveToNext()){
            if(cursor.getInt(1)==num)
                return cursor.getString(2);
        }
        return "찾을 수 없습니다.";
    }

    public int findall(int num){
        //전체양을 DB에서 받아오기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        Cursor cursor = db.rawQuery("SELECT * FROM Medicine", null);
        while (cursor.moveToNext()){
            if(cursor.getInt(1)==num)
                return cursor.getInt(6);
        }
        return 0000;
    }

    public String findimg(int num){
        //알약의 image를 DB에서 불러오기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        Cursor cursor = db.rawQuery("SELECT * FROM Medicine", null);
        while (cursor.moveToNext()){
            if(cursor.getInt(1)==num)
                return cursor.getString(9);
        }
        return "찾을 수 없습니다.";
    }

    public int findone(int num){
        //1회 복용량을 DB에서 불러오기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        Cursor cursor = db.rawQuery("SELECT * FROM Medicine", null);
        while (cursor.moveToNext()){
            if(cursor.getInt(1)==num)
                return cursor.getInt(7);
        }
        return 0000;
    }
}