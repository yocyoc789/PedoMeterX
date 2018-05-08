package com.example.student.pedometerx;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBclass extends SQLiteOpenHelper {

    public DBclass(Context context){
        super(context, "pedometer.db",null,1);
    }

    public void adddailyrecord(String date, int steps,int stepgoal, double calburned, double distance, double speed, String status,long time){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues p = new ContentValues();
        p.put("DATE", date);
        p.put("STEPS", steps);
        p.put("STEPGOAL", stepgoal);
        p.put("CALBURNED", calburned);
        p.put("DISTANCE", distance);
        p.put("SPEED", speed);
        p.put("STATUS", status);
        p.put("TIME", time);
        db.insert("DAILYRECORD",null,p);

    }
    public void updatedailyrecord(String date, int steps,int stepgoal, double calburned, double distance, double speed, String status,long time){
        SQLiteDatabase db = this.getReadableDatabase();
        String strSQL = "UPDATE DAILYRECORD SET DATE = '"+date+"', STEPS = '"+steps+"', STEPGOAL = '"+stepgoal+"', CALBURNED = '"+calburned+",DISTANCE = '"+distance+"', SPEED = '"+speed+"',STATUS = '"+status+"', TIME '"+time+"'" ;
        db.execSQL(strSQL);
        db.close();
    }
    public void updatestatus(String status){
        SQLiteDatabase db = this.getReadableDatabase();
        String strSQL = "UPDATE DAILYRECORD SET STATUS = '"+status+"'";
        db.execSQL(strSQL);
        db.close();
    }
    public void updatetime(long time){
        SQLiteDatabase db = this.getReadableDatabase();
        String strSQL = "UPDATE DAILYRECORD SET TIME ='"+time+"'";
        db.execSQL(strSQL);
        db.close();
    }
//    public void deleteStudent(String nameid){
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("DELETE FROM students WHERE NAME= '"+nameid+"'");
//        db.close();
//    }
    public ArrayList<dailyrecord> selectDailyrecords() {
        ArrayList<dailyrecord> result = new ArrayList<dailyrecord>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM DAILYRECORD",null);
        c.moveToFirst();
        while (c.isAfterLast() == false){
            String date = c.getString(c.getColumnIndex("DATE"));
            String status = c.getString(c.getColumnIndex("STATUS"));
            int step = c.getInt(c.getColumnIndex("STEPS"));
            int stepgoal = c.getInt(c.getColumnIndex("STEPGOAL"));
            double speed = c.getDouble(c.getColumnIndex("SPEED"));
            double calburned = c.getDouble(c.getColumnIndex("CALBURNED"));
            double distance = c.getDouble(c.getColumnIndex("DISTANCE"));

            dailyrecord dr = new dailyrecord(date,step,stepgoal,speed,calburned,distance,status);
            result.add(dr);
            c.moveToNext();
        }
        return result;
    }

    public DBclass(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBclass(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public String getDatabaseName() {
        return super.getDatabaseName();
    }

    @Override
    public void setWriteAheadLoggingEnabled(boolean enabled) {
        super.setWriteAheadLoggingEnabled(enabled);
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }

    @Override
    public synchronized void close() {
        super.close();
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE DAILYRECORD (DATE TEXT,STEPS INT,STEPGOAL INT, CALBURNED DOUBLE, DISTANCE DOUBLE, SPEED DOUBLE, STATUS TEXT, LONG TIME)");
        sqLiteDatabase.execSQL("CREATE TABLE appstatus (STATUS TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
}
