package com.example.student.pedometerx;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class addWeightDialog extends AppCompatDialogFragment {
    DBclass db;
    EditText weight;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_weight,null);

        db = new DBclass(getActivity());

        weight = (EditText) view.findViewById(R.id.editweight);

        builder.setView(view)
                .setTitle("Set Weight")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(!weight.getText().equals("")){
                            db.updateweight(Double.parseDouble(String.valueOf(weight.getText())));
                            SettingsFragment.tvweight.setText(Double.parseDouble(String.valueOf(weight.getText()))+" KG");
                        }
                        else{
                            Toast.makeText(getActivity(),"Please insert required fields",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        return builder.create();
    }
}
