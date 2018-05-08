package com.example.student.pedometerx;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
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

import java.util.ArrayList;

public class HomeFragment extends Fragment{

    static TextView textView,TvSteps;
    Button btnplaypause;
    boolean running = true;
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private static final String TEXT_NUM_STEPS = "Number of Steps: ";
    static PieChart mPieChart;
    public DBclass db;
    public static String curstatus="";

    @Nullable

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_home,container,false);
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        db = new DBclass(getActivity());
        TvSteps=(TextView)v.findViewById(R.id.tv_steps);
        btnplaypause= (Button)v.findViewById(R.id.btnplaypause);
        TvSteps.setText(""+MainActivity.numSteps);
        mPieChart = (PieChart) v.findViewById(R.id.piechart);

        mPieChart.addPieSlice(new PieModel("Achieved", MainActivity.numSteps, Color.parseColor("#56B7F1")));
        mPieChart.addPieSlice(new PieModel("Empty", 100 - MainActivity.numSteps, Color.parseColor("#CDA67F")));

        mPieChart.setDrawValueInPie(false);

        mPieChart.startAnimation();

        ArrayList<dailyrecord> dr = db.selectDailyrecords();
        if (dr.size() == 0){
            db.adddailyrecord(MainActivity.getdatetom(),0,0,0.0,0.0,0.0,"pause",0);
        }

        //verify if another day
        MainActivity.newday();

        curstatus = dr.get(dr.size()-1).status;
        Toast.makeText(getActivity(),dr.size()+" "+curstatus,Toast.LENGTH_LONG).show();

        if (curstatus.equals("pause")){
            btnplaypause.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_play_arrow_black_24dp));
        }
        else{
            getActivity().startService(new Intent(getActivity(), MyService.class));
            btnplaypause.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_pause_black_24dp));
            getActivity().startService(new Intent(getActivity(), MyService.class));
        }

        //clicklisteners for pausing and playing
        btnplaypause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<dailyrecord> dr = db.selectDailyrecords();
                curstatus = dr.get(dr.size()-1).status;
                if (curstatus.equals("play")){
                    btnplaypause.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_play_arrow_black_24dp));
                    getActivity().stopService(new Intent(getActivity(), MyService.class));
                    db.updatestatus("pause");
                }
                else{
                    btnplaypause.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_pause_black_24dp));
                    getActivity().startService(new Intent(getActivity(), MyService.class));
                    db.updatestatus("play");
                }


            }
        });

        return v;


    }
    public static void updatechart(){
        TvSteps.setText("" + MainActivity.numSteps);

       }


//    public static void changeFragmentTextView(String s) {
//        Fragment frag = getFragmentManager().findFragmentById(new HomeFragment());
//        ((TextView) frag.getView().findViewById(R.id.textView)).setText(s);
//
//    }
        public static void setText(String text) {
            TvSteps.setText(text);
            mPieChart.clearChart();
            mPieChart.addPieSlice(new PieModel("Achieved", MainActivity.stepz, Color.parseColor("#56B7F1")));
            mPieChart.addPieSlice(new PieModel("Empty", 100 - MainActivity.stepz, Color.parseColor("#CDA67F")));

        }

}
