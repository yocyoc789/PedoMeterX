package com.example.student.pedometerx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.ChartData;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class GraphFragment extends Fragment {
    ListView lvgraph,lvtimline;
    public Button timelviewl,backbtn;
    static ConstraintLayout constraintLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_graphs, container, false);
        BarChart mBarChart = (BarChart) v.findViewById(R.id.barchart);

        lvgraph = (ListView)v.findViewById(R.id.lvgraph);
        GraphFragment.CustomAdapter customAdapter = new GraphFragment.CustomAdapter();
        lvgraph.setAdapter(customAdapter);

        lvtimline = (ListView)v.findViewById(R.id.lvtimeline);
        GraphFragment.TimeLineAdapter timeLineAdapter = new GraphFragment.TimeLineAdapter();
        lvtimline.setAdapter(timeLineAdapter);

        backbtn =(Button)v.findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new GraphFragment()).commit();
            }
        });

        constraintLayout = (ConstraintLayout)v.findViewById(R.id.timelinelayout);
        timelviewl =(Button)v.findViewById(R.id.btntimeview);
        timelviewl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                constraintLayout.setVisibility(View.VISIBLE);
                timelviewl.setVisibility(View.INVISIBLE);
            }
        });

        return v;
    }
    class CustomAdapter extends BaseAdapter{
        DBclass db = new DBclass(getActivity());
        ArrayList<dailyrecord> dr = db.selectDailyrecords();

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View v = getLayoutInflater().inflate(R.layout.graph_report_layout,null);
            TextView tvtype = (TextView)v.findViewById(R.id.txtgtype);
            BarChart mBarChart = (BarChart) v.findViewById(R.id.barchart);
            TextView txty1 = (TextView)v.findViewById(R.id.txty1);
            TextView txty2 = (TextView)v.findViewById(R.id.txty2);
            TextView txty3 = (TextView)v.findViewById(R.id.txty3);
            TextView txty4 = (TextView)v.findViewById(R.id.txty4);


            List l = new ArrayList();
            for(int x=0;x<dr.size();x++){
                if(i==0){
                    tvtype.setText("STEPS");
                    mBarChart.addBar(new BarModel(dr.get(x).dates+"",dr.get(x).steps, 0xFF123456));
                    l.add(dr.get(x).steps);
                }
                else if (i==1){
                    double d = dr.get(x).speeds;
                    float f = (float)d+.0f;
                    tvtype.setText("SPEED");
                    mBarChart.addBar(new BarModel(dr.get(x).dates+"", f, 0xFF563456));
                    l.add((f));
                }
                else if (i==2){
                    tvtype.setText("DISTANCE");
                    double d = dr.get(x).distances;
                    float f = (float)d;
                    mBarChart.addBar(new BarModel(dr.get(x).dates+"",f, 0xFF873F56));
                    l.add(f);

                }
                else if (i==3){
                    tvtype.setText("CALORIE BURNED");
                    double d = dr.get(x).calburned;
                    float f = (float)d;
                    mBarChart.addBar(new BarModel(dr.get(x).dates+"",f, 0xFF1FF4AC));
                    l.add(f);
                }
            }
            if(i == 0){
                int q = Integer.parseInt(String.valueOf(Collections.max(l)));
                txty1.setText(Collections.max(l)+"");
                txty2.setText(q/2+"");
                txty3.setText(q/3+"");
                txty4.setText(q/4+"");
            }
            else{
                double q = Double.parseDouble(String.valueOf(Collections.max(l)));
                txty1.setText(String.format("%.2f",q).replaceAll("0*$","")+"");
                txty2.setText(String.format("%.2f",q/2).replaceAll("0*$","")+"");
                txty3.setText(String.format("%.2f",q/3).replaceAll("0*$","")+"");
                txty4.setText(String.format("%.2f",q/4).replaceAll("0*$","")+"");
            }



            mBarChart.startAnimation();
            return v;
        }
    }
    class TimeLineAdapter extends BaseAdapter{
        DBclass db = new DBclass(getActivity());
        ArrayList<dailyrecord> dr = db.selectDailyrecords();
        @Override
        public int getCount() {
            return dr.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View v = getLayoutInflater().inflate(R.layout.timeline_list,null);
            TextView txtdate =(TextView)v.findViewById(R.id.txtdatel);
            TextView txtcb = (TextView)v.findViewById(R.id.tvcalburnedl);
            TextView txtspeed= (TextView)v.findViewById(R.id.tvspeedl);
            TextView txtdis=(TextView)v.findViewById(R.id.tvdistancel);
            TextView txttime = (TextView)v.findViewById(R.id.tvttimel);

//            DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
//            String datet = dateFormat.format((Date));

            txtdate.setText(dr.get(i).dates);
            txtcb.setText(dr.get(i).calburned+" kcal");
            txtspeed.setText(dr.get(i).speeds+" mile/hour");
            txtdis.setText(dr.get(i).distances+" miles");
            txttime.setText(dr.get(i).time+"");

            return v;
        }
    }
    public static Boolean chkifvisible(){
        Boolean b=false;
        if(constraintLayout.getVisibility() == View.VISIBLE){
            b=true;
        }
        return b;
    }
}
