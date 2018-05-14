package com.example.student.pedometerx;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class addAchievementDialog extends AppCompatDialogFragment {
    Spinner spintype,spinsets;
    TextView txttitle,txttotal,txtunit;
    DBclass db;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_achievment,null);

        db = new DBclass(getActivity());
        txttitle = (TextView)view.findViewById(R.id.txttitle);
        txttotal = (TextView)view.findViewById(R.id.txttotal);
        spintype = (Spinner)view.findViewById(R.id.spinnertype);
        spinsets = (Spinner)view.findViewById(R.id.spinnersets);
        txtunit = (TextView)view.findViewById(R.id.txtunit);
        spinsets.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayList<Achievement> am = db.selectAchievement();
                if(spinsets.getSelectedItem().equals("speed")){
                    txtunit.setText("mile/hour");
                }
                else if(spinsets.getSelectedItem().equals("distance")){
                    txtunit.setText("mile");
                }
                else if(spinsets.getSelectedItem().equals("Calories Burn")){

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        builder.setView(view)
                .setTitle("Add New Achievment")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ArrayList<dailyrecord> dr = db.selectDailyrecords();
                        String title = txttitle.getText().toString();
                        String type = (String) spintype.getSelectedItem();
                        double total = Double.parseDouble(String.valueOf(txttotal.getText()));
                        int sets = (int) spinsets.getSelectedItem();
                        db.addnewachievement(dr.size(),title,type,total,sets,"unfinished","no");
                    }
                });
    return builder.create();
        }
    }
