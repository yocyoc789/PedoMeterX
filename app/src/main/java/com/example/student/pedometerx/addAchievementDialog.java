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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;
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
        spintype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayList<Achievement> am = db.selectAchievement();
                if(spintype.getSelectedItem().equals("Speed")){
                    txtunit.setText("mile/hour");
                }
                else if(spintype.getSelectedItem().equals("Distance")){
                    txtunit.setText("miles");
                }
                else if(spintype.getSelectedItem().equals("Burn Calories")){
                    txtunit.setText("kcal");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        builder.setView(view)
                .setTitle("Add New Achievement")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ArrayList<Achievement> am = db.selectAchievement();
                        String title = txttitle.getText().toString();
                        String type = (String) spintype.getSelectedItem();
                        double total = Double.parseDouble(String.valueOf(txttotal.getText()));
                        int sets = Integer.parseInt(String.valueOf(spinsets.getSelectedItem()));

                        if(txttitle.getText().equals("") && txttotal.getText().equals("")){
                            Toast.makeText(getActivity(),"Please fill all fields",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            db.addnewachievement(am.size()+1,MainActivity.getdatetod(), title,type,total,sets,0,"unfinished","no");
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new SettingsFragment()).commit();

                        }
                    }
                });
    return builder.create();
        }
    }

