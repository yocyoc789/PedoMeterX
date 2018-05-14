package com.example.student.pedometerx;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static android.media.MediaExtractor.MetricsConstants.FORMAT;

public class HomeFragment extends Fragment{

    static TextView textView,TvSteps;
    Button btnplaypause;
    static PieChart mPieChart;
    public static DBclass db;
    public static String curstatus="";
    static TextView tvspeed,tvdistance,tvcalburned,tvtimer,txtstepgoal;

    @Nullable

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_home,container,false);
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        db = new DBclass(getActivity());
        TvSteps=(TextView)v.findViewById(R.id.tv_steps);
        btnplaypause= (Button)v.findViewById(R.id.btnplaypause);
        TvSteps.setText(""+MainActivity.numSteps);
        mPieChart = (PieChart) v.findViewById(R.id.piechart);
        mPieChart.setDrawValueInPie(false);

        tvspeed = (TextView)v.findViewById(R.id.txtspeed);
        tvdistance = (TextView)v.findViewById(R.id.txtdistance);
        tvcalburned= (TextView)v.findViewById(R.id.txtcalburned);
        tvtimer=(TextView)v.findViewById(R.id.timer);
        txtstepgoal=(TextView)v.findViewById(R.id.txtstepgoal);

        ArrayList<dailyrecord> dr = db.selectDailyrecords();
        if (dr.size() == 0){
            db.adddailyrecord(MainActivity.getdatetod(),0,0,0.0,0.0,0.0,"pause",0);
            db.adduserinfo(50.0, 2.5);
        }
        //verify if another day
        MainActivity.newday();

        ArrayList<Userinfo> ui = db.selectUserInfo();
        Toast.makeText(getActivity(), ui.get(ui.size()-1).stepdis+"", Toast.LENGTH_SHORT).show();
        dr = db.selectDailyrecords();
        curstatus = dr.get(dr.size()-1).status;
        Toast.makeText(getActivity(),dr.size()+" "+curstatus,Toast.LENGTH_LONG).show();

        //add data to current


        if (curstatus.equals("pause")){
            btnplaypause.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_play_arrow_black_24dp));
        }
        else{
            btnplaypause.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_pause_black_24dp));
            if(isMyServiceRunning(MyService.class,getActivity())){

            }
            else{
                getActivity().startService(new Intent(getActivity(), MyService.class));
            }
        }

        TvSteps.setText(dr.get(dr.size()-1).steps+"");
        tvdistance.setText(dr.get(dr.size()-1).distances+" mile");
        tvspeed.setText(dr.get(dr.size()-1).speeds+" mile/h");
        tvtimer.setText(String.format("%02d:%02d:%02d", dr.get(dr.size()-1).time / 3600,
                (dr.get(dr.size()-1).time % 3600) / 60, (dr.get(dr.size()-1).time % 60)));
        txtstepgoal.setText("Goal\n"+dr.get(dr.size()-1).stepsgoal+"");
        tvcalburned.setText(dr.get(dr.size()-1).calburned+" calories");

        updatechart();
        mPieChart.startAnimation();
        //clicklisteners for pausing and playing
        btnplaypause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<dailyrecord> dr = db.selectDailyrecords();
                curstatus = dr.get(dr.size()-1).status;
                if (curstatus.equals("play")){
                    btnplaypause.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_play_arrow_black_24dp));
                    db.updatestatus("pause");
                    getActivity().stopService(new Intent(getActivity(), MyService.class));

                }
                else{
                    btnplaypause.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_pause_black_24dp));
                    db.updatestatus("play");
                    getActivity().startService(new Intent(getActivity(), MyService.class));

                }


            }
        });
        return v;
    }
    public boolean isMyServiceRunning(Class<?> serviceClass,Context c) {
        ActivityManager manager = (ActivityManager) c.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


        public static void setText(String text) {
            if (TvSteps != null) {
                TvSteps.setText(text);
                mPieChart.clearChart();
                updatechart();
            }
    }
        public static void updatechart() {
            if (TvSteps != null) {
                ArrayList<dailyrecord> dr = db.selectDailyrecords();
                mPieChart.addPieSlice(new PieModel("Empty", dr.get(dr.size()-1).stepsgoal - dr.get(dr.size() - 1).steps, Color.parseColor("#A9A9A9")));
                mPieChart.addPieSlice(new PieModel("Achieved", dr.get(dr.size() - 1).steps, Color.parseColor("#56B7F1")));
                 }
        }

        public static void calcldistance(double distance,Context context){
            DBclass dbl = new DBclass(context);
            ArrayList<dailyrecord> dr = dbl.selectDailyrecords();
            ArrayList<Userinfo> ui = dbl.selectUserInfo();
            double converttomiles = ui.get(ui.size()-1).stepdis * 0.00018939;
            distance = dr.get(dr.size()-1).steps * converttomiles;
            String rounded = String.format("%.6f",distance).replaceAll("0*$","");
            dbl.updatedistance(Double.parseDouble(rounded));
            if(tvdistance!=null){
                tvdistance.setText(rounded+" mile");
            }
        }

        public static void calcspeed(double getdistance,Context context){
            DBclass dbl = new DBclass(context);
            ArrayList<dailyrecord> dr = dbl.selectDailyrecords();
            Double speed = getdistance/dr.get(dr.size()-1).time*(3600);
            String rounded = String.format("%.6f",speed).replaceAll("0*$","");
            dbl.updatedspeed(Double.parseDouble(rounded));
            if(tvspeed!=null){
                tvspeed.setText(rounded +" mile/h");
            }
        }
        public static void calculatetime(String time){
            if (tvtimer!= null) {
                tvtimer.setText(time);
            }
        }
        public static void calculatecalburned(Context context){
            DBclass dbl = new DBclass(context);
            ArrayList<Userinfo> ui = dbl.selectUserInfo();
            ArrayList<dailyrecord> dr = dbl.selectDailyrecords();
            double kgtopounds = ui.get(ui.size()-1).weight * 2.20462262;
            double calburned = dr.get(dr.size()-1).distances * kgtopounds;
            String rounded = String.format("%.2f",calburned).replaceAll("0*$","");

        }
}
