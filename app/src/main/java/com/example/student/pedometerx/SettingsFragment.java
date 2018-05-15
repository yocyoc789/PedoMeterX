package com.example.student.pedometerx;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import at.markushi.ui.CircleButton;

public class SettingsFragment extends Fragment {
    AlertDialog.Builder builder;
    CircleButton btnaddnewA;
    Button btnweight;
    Spinner spinnergoal, spinnerstepdis;
    static TextView tvweight;
    ListView listView;
    public DBclass db;

    int[] IMAGES = {R.drawable.ic_pause_black_24dp,R.drawable.ic_pause_black_24dp,R.drawable.ic_pause_black_24dp,R.drawable.ic_pause_black_24dp,R.drawable.ic_pause_black_24dp};
    String[] NAMES = {"Add black","Equalizer black","GPS qwerty","asdasdas","sadasdas"};
    String[] Description = {"asd1","asd2","asd3","asd4","asd5"};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_settings, container, false);

        db = new DBclass(getActivity());

        spinnergoal = (Spinner)v.findViewById(R.id.spinnergoal);
        spinnerstepdis = (Spinner)v.findViewById(R.id.spinnersteplen);
        tvweight = (TextView)v.findViewById(R.id.txtweight);

        //Set text for goal and user info
        ArrayList<Userinfo> ui = db.selectUserInfo();
        for(int i=0;i<spinnerstepdis.getCount();i++){
             if(String.valueOf(ui.get(ui.size()-1).stepdis+" FT").equals(spinnerstepdis.getItemAtPosition(i))){
                spinnerstepdis.setSelection(i);
            }
        }
        ArrayList<dailyrecord> dr = db.selectDailyrecords();
        for(int i =0;i<spinnergoal.getCount();i++){
            if(String.valueOf(dr.get(dr.size()-1).stepsgoal).equals(spinnergoal.getItemAtPosition(i))){
                spinnergoal.setSelection(i);
            }
        }
        tvweight.setText(ui.get(ui.size()-1).weight+" KG");

        listView = (ListView)v.findViewById(R.id.listview);
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);

        btnaddnewA = (CircleButton)v.findViewById(R.id.btnaddnewA);
        btnweight = (Button)v.findViewById(R.id.btnweight);

        builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Title");
        LinearLayout layout = new LinearLayout(getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);

        final Spinner type = new Spinner(getActivity());
        List<String> list = new ArrayList<String>();
        list.add("Steps");
        list.add("Distance");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(dataAdapter);

        btnaddnewA.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                addAchievement();
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = ((TextView) view.findViewById(R.id.txttitle)).getText().toString();

                Toast toast = Toast.makeText(getActivity(), selected, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        btnweight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addWeight();
            }
        });



        //when an item is selected in spinners

        //*STEPGOAL
        spinnergoal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                db.updategoal(Integer.parseInt((String) spinnergoal.getSelectedItem()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        //*USER STEP DISTANCE
        spinnerstepdis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String str = spinnerstepdis.getSelectedItem()+"";
                str = str.replaceAll("[^\\d.]", "");
                db.updatestepdis(Double.parseDouble(str));
                }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        return v;
    }
    class CustomAdapter extends BaseAdapter {
        DBclass db = new DBclass(getActivity());
        ArrayList<Achievement> am = db.selectAchievement();
        ArrayList<dailyrecord> dr = db.selectDailyrecords();
        ArrayList<String> title = new ArrayList<>();
        ArrayList<String> total = new ArrayList<>();
        ArrayList<String> sets = new ArrayList<>();

        @Override
        public int getCount() {
            return am.size();
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
            View v = getLayoutInflater().inflate(R.layout.achievement_layout,null);
            ImageView img1 = (ImageView) v.findViewById(R.id.img1);
            TextView txttitle = (TextView) v.findViewById(R.id.txttitle);
            TextView txttotaltype = (TextView) v.findViewById(R.id.txttypetotal);
            TextView txtsets = (TextView) v.findViewById(R.id.txtsets);
            TextView txtid = (TextView)v.findViewById(R.id.txtid);
//asd
            String getdattod = "Created on "+am.get(i).datecreated;
            if(am.get(i).status.equals("unfinished")){
                if(am.get(i).type.equals("Speed")){
                    img1.setImageResource(R.drawable.speed);
                    txttotaltype.setText("Speed up to "+am.get(i).total+" mile/h\n"+getdattod);
                }
                else if(am.get(i).type.equals("Distance")){
                    img1.setImageResource(R.drawable.distance);
                    txttotaltype.setText("Reach "+am.get(i).total+" miles\n"+getdattod);
                }
                else{
                    img1.setImageResource(R.drawable.calory);
                    txttotaltype.setText("Burn "+am.get(i).total+" calories\n"+getdattod);
                }
                txttitle.setText(am.get(i).title);
                txtsets.setText(am.get(i).setsachieved+"/"+am.get(i).sets);
                txtid.setText(am.get(i).id+"");
            }


            return v;
        }



    }
    public void addAchievement() {

        addAchievementDialog addAchievemen = new addAchievementDialog();
        addAchievemen.show(getFragmentManager(),"Add Dialog");
    }
    public void addWeight(){
        addWeightDialog addw = new addWeightDialog();
        addw.show(getFragmentManager(),"Add Dialog");
    }

}
