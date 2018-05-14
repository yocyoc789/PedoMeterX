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

    public void adddailyrecord(String date, long steps,long stepgoal, double calburned, double distance, double speed, String status,long time){
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
    public void addnewachievement(int id, String title, String type, double total, int sets, String status, String stattoday){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues p = new ContentValues();
        p.put("ID", id);
        p.put("TITLE", title);
        p.put("TYPE", type);
        p.put("TOTAL", total);
        p.put("SETS", sets);
        p.put("STATUS", status);
        p.put("STATTODAY", stattoday);
        db.insert("ACHIEVEMENT",null,p);


    }
    public void adduserinfo(double weight, double stepdis){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues p = new ContentValues();
        p.put("WEIGHT", weight);
        p.put("STEPDIS", stepdis);
        db.insert("USERINFO",null,p);
    }

    public void updatedailyrecord(String date, long steps,long stepgoal, double calburned, double distance, double speed, String status,long time){
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
    public void updatesteps(long steps){
        SQLiteDatabase db = this.getReadableDatabase();
        String strSQL = "UPDATE DAILYRECORD SET STEPS = '"+steps+"'";
        db.execSQL(strSQL);
        db.close();
    }
    public void updateDistanceSpeedCalBurned(double distance, double speed, double calburned){
        SQLiteDatabase db = this.getReadableDatabase();
        String strSQL = "UPDATE DAILYRECORD SET DISTANCE = '"+distance+"', SPEED = '"+speed+"', CALBURNED = '"+calburned+"'";
        db.execSQL(strSQL);
        db.close();
    }
    public void updatedistance(double distance){
        SQLiteDatabase db = this.getReadableDatabase();
        String strSQL = "UPDATE DAILYRECORD SET DISTANCE = '"+distance+"'";
        db.execSQL(strSQL);
        db.close();
    }
    public void updatedspeed(double speed){
        SQLiteDatabase db = this.getReadableDatabase();
        String strSQL = "UPDATE DAILYRECORD SET SPEED = '"+speed+"'";
        db.execSQL(strSQL);
        db.close();
    }
    public void updatetime(long time){
        SQLiteDatabase db = this.getReadableDatabase();
        String strSQL = "UPDATE DAILYRECORD SET TIME ='"+time+"'";
        db.execSQL(strSQL);
        db.close();
    }
    public void updateweight(double weight){
        SQLiteDatabase db = this.getReadableDatabase();
        String strSQL = "UPDATE USERINFO SET WEIGHT ='"+weight+"'";
        db.execSQL(strSQL);
        db.close();
    }
    public void updatestepdis(double stepdis){
        SQLiteDatabase db = this.getReadableDatabase();
        String strSQL = "UPDATE USERINFO SET STEPDIS ='"+stepdis+"'";
        db.execSQL(strSQL);
        db.close();
    }
    public void updategoal(long goal){
        SQLiteDatabase db = this.getReadableDatabase();
        String strSQL = "UPDATE DAILYRECORD SET STEPGOAL ='"+goal+"'";
        db.execSQL(strSQL);
        db.close();
    }
    public void updatecalburned(double calburned){
        SQLiteDatabase db = this.getReadableDatabase();
        String strSQL = "UPDATE DAILYRECORD SET CALBURNED ='"+calburned+"'";
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
            long step = c.getLong(c.getColumnIndex("STEPS"));
            long stepgoal = c.getLong(c.getColumnIndex("STEPGOAL"));
            double speed = c.getDouble(c.getColumnIndex("SPEED"));
            double calburned = c.getDouble(c.getColumnIndex("CALBURNED"));
            double distance = c.getDouble(c.getColumnIndex("DISTANCE"));
            long time = c.getLong(c.getColumnIndex("TIME"));

            dailyrecord dr = new dailyrecord(date,step,stepgoal,speed,calburned,distance,status,time);
            result.add(dr);
            c.moveToNext();
        }
        return result;
    }
    public ArrayList<Achievement> selectAchievement(){
        ArrayList<Achievement> result = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM ACHIEVEMENT",null);
        c.moveToFirst();
        while (c.isAfterLast() == false){
            int id = c.getInt(c.getColumnIndex("ID"));
            int sets= c.getInt(c.getColumnIndex("SETS"));
            String title = c.getString(c.getColumnIndex("TITLE"));
            String type = c.getString(c.getColumnIndex("TYPE"));
            String status = c.getString(c.getColumnIndex("STATUS"));
            String stattoday = c.getString(c.getColumnIndex("STATTODAY"));
            double total = c.getDouble(c.getColumnIndex("TOTAL"));
            Achievement achievement = new Achievement(id,title,type,total,sets,status,stattoday);
            result.add(achievement);
            c.moveToNext();
        }
       return result;
    }
    public ArrayList<Userinfo> selectUserInfo(){
        ArrayList<Userinfo> result = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM USERINFO",null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            double weight = c.getDouble(c.getColumnIndex("WEIGHT"));
            double stepdis = c.getDouble(c.getColumnIndex("STEPDIS"));
            Userinfo ui = new Userinfo(weight,stepdis);
            result.add(ui);
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
        sqLiteDatabase.execSQL("CREATE TABLE DAILYRECORD (DATE TEXT,STEPS LONG,STEPGOAL LONG, CALBURNED DOUBLE, DISTANCE DOUBLE, SPEED DOUBLE, STATUS TEXT, TIME LONG)");
        sqLiteDatabase.execSQL("CREATE TABLE ACHIEVEMENT (ID INT, TITLE TEXT, TYPE TEXT, TOTAL DOUBLE, SETS INT, STATUS TEXT, STATTODAY TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE USERINFO (WEIGHT DOUBLE, STEPDIS DOUBLE)");
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
