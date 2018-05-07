package com.example.student.pedometerx;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class MyService extends Service implements SensorEventListener, StepListener {
    private SensorManager sensorManager;
    private Sensor accel;
    private StepDetector simpleStepDetector;
    private int steps=0;
    private NotificationManager notificationManager;
    private MediaPlayer player;
    private Notification.Builder builder;
    private Notification notification;
    private DBclass db;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(MyService.this, accel, SensorManager.SENSOR_DELAY_FASTEST);
        snotify();
        player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
        player.setLooping(true);



        db = new DBclass(this);
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        notificationManager.cancel(1);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                simpleStepDetector.updateAccel(
                        event.timestamp, event.values[0], event.values[1], event.values[2]);

            }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void step(long timeNs) {
        MainActivity.stepz++;
        HomeFragment.updatechart();
       snotify();
    }

    public void snotify(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("RSS");

        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(""));
        @SuppressLint("WrongConstant") PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(),0, myIntent,Intent.FLAG_ACTIVITY_NEW_TASK);
        Context context = getApplicationContext();

        Spannable sb = new SpannableString("Steps Today");
        sb.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, 11, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        builder= new Notification.Builder(context)
                .setContentTitle("Steps Today")
                .setContentText(""+MainActivity.stepz)
                .setContentIntent(pendingIntent)
                //.setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(false)
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_directions_walk_black_24dp);

        notification = builder.build();
        notificationManager =(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1,notification);
    }
    public void updatedatabase(){

        ArrayList<dailyrecord> dr = db.selectDailyrecords();
        if(dr.size() == 0){

        }
        else if (dr.get(dr.size()).dates.equals(MainActivity.getdatetom())){
            addnew();
        }

    }

    public void addnew(){
        db.adddailyrecord(MainActivity.getdatetom(),0,0,0.0,0.0,0.0,"paused");
    }
}
