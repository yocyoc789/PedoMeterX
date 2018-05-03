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
import android.widget.TextView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class HomeFragment extends Fragment implements SensorEventListener, StepListener{

    TextView textView,TvSteps;
    Button btn_stop,btn_Start;
    boolean running = true;
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private static final String TEXT_NUM_STEPS = "Number of Steps: ";
    private PieChart mPieChart;

    @Nullable

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_home,container,false);
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        TvSteps=(TextView)v.findViewById(R.id.tv_steps);
        btn_stop=(Button)v.findViewById(R.id.btn_stop);
        btn_Start=(Button)v.findViewById(R.id.btn_start);
        TvSteps.setText(""+MainActivity.numSteps);
        mPieChart = (PieChart) v.findViewById(R.id.piechart);

       // mPieChart.addPieSlice(new PieModel("Freetime", 15, Color.parseColor("#FE6DA8")));
        mPieChart.addPieSlice(new PieModel("Achieved", MainActivity.numSteps, Color.parseColor("#56B7F1")));
        mPieChart.addPieSlice(new PieModel("Empty", 100 - MainActivity.numSteps, Color.parseColor("#CDA67F")));
        //mPieChart.addPieSlice(new PieModel("Eating", 9, Color.parseColor("#FED70E")));
        //mPieChart.setLabelFor();
        mPieChart.setDrawValueInPie(false);

        mPieChart.startAnimation();


        //when buttons are clicked
        btn_Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startService(new Intent(getActivity(), MyService.class));
                running = true;
                sensorManager.registerListener(HomeFragment.this, accel, SensorManager.SENSOR_DELAY_FASTEST);
                 }
        });
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                running = false;
                getActivity().stopService(new Intent(getActivity(), MyService.class));
                //sensorManager.unregisterListener(HomeFragment.this);
            }
        });
        return v;


    }



    @Override
    public void onResume() {
        super.onResume();
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


    }

    @Override
    public void onPause() {
        super.onPause();
        running = false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (running){
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                simpleStepDetector.updateAccel(
                        event.timestamp, event.values[0], event.values[1], event.values[2]);
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    public void step(long timeNs) {
        MainActivity.numSteps++;
        TvSteps.setText("" + MainActivity.numSteps);
        mPieChart.clearChart();
        mPieChart.addPieSlice(new PieModel("Achieved", MainActivity.numSteps, Color.parseColor("#56B7F1")));
        mPieChart.addPieSlice(new PieModel("Empty", 100 - MainActivity.numSteps, Color.parseColor("#CDA67F")));

    }
}
