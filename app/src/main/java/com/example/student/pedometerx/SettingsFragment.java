package com.example.student.pedometerx;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import at.markushi.ui.CircleButton;

public class SettingsFragment extends Fragment {
    AlertDialog.Builder builder;
    CircleButton btnaddnewA;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        btnaddnewA = (CircleButton)v.findViewById(R.id.btnaddnewA);

//jhk
        builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Title");
        LinearLayout layout = new LinearLayout(getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);
// Set up the input
        final Spinner type = new Spinner(getActivity());
        List<String> list = new ArrayList<String>();
        list.add("Steps");
        list.add("Distance");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(dataAdapter);
        final EditText goalcount = new EditText(getActivity());
        goalcount.setInputType(InputType.TYPE_CLASS_NUMBER);
        layout.addView(type);
        layout.addView(goalcount);

        builder.setView(layout);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        //input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

// Set up the buttons

//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                //m_Text = input.getText().toString();
//            }
//        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
        btnaddnewA.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                addAchievement();
            }
        });

//        LinearLayout settinglist = (LinearLayout)v.findViewById(R.id.settingsLayout);
//        settinglist.setOrientation(LinearLayout.VERTICAL);
//
//        //Set goal
//        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(140,398);
//        param.setMargins(20,20,20,20);
//        LinearLayout content1 = new LinearLayout(getActivity());
//        content1.setOrientation(LinearLayout.HORIZONTAL);
//
//        ImageView img1 = new ImageButton(getActivity());
//        img1.setImageResource(R.drawable.ic_gps_fixed_black_24dp);
//        TextView tv = new TextView(getActivity());
//        tv.setText("Goals");
//        final Spinner type1 = new Spinner(getActivity());
//        List<String> list1 = new ArrayList<String>();
//        list1.add("1000");
//        list1.add("2000");
//        list1.add("3000");
//        list1.add("4000");
//        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list1);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        type1.setAdapter(dataAdapter1);
//        content1.addView(img1);
//        content1.addView(tv);
//        content1.addView(type1);
//        settinglist.addView(content1,param);
        return v;
    }
    public void addAchievement() {

        addAchievementDialog addAchievemen = new addAchievementDialog();
        addAchievemen.show(getFragmentManager(),"Add Dialog");
    }

}
