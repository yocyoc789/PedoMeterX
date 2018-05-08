package com.example.student.pedometerx;

import android.app.Fragment;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity  {
    static int numSteps=0;
    static int stepz;
    android.support.v4.app.Fragment selectedFragment=null;
    static DBclass db;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.navigation_graphs:
                    selectedFragment = new GraphFragment();
                    break;
                case R.id.navigation_settings:
                    selectedFragment = new SettingsFragment();

                    break;
            }
           getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();

        db = new DBclass(this);
    }

    public void sa(View v){

    }
    public static String getdatetom(){
        GregorianCalendar gc = new GregorianCalendar();
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        gc.add(Calendar.DATE, 1);
        Date tomorrow = gc.getTime();
        String datet = dateFormat.format(tomorrow);
        return datet;
    }
    public static String getdatetod(){
        GregorianCalendar gc = new GregorianCalendar();
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        gc.add(Calendar.DATE, 0);
        Date tomorrow = gc.getTime();
        String datet = dateFormat.format(tomorrow);
        return datet;
    }
    public static void newday(){
        ArrayList<dailyrecord> dr = db.selectDailyrecords();
        if (!dr.get(dr.size()-1).dates.equals(MainActivity.getdatetod())){
            db.adddailyrecord(MainActivity.getdatetom(),0,0,0.0,0.0,0.0,"pause",0);
        }
    }
}
