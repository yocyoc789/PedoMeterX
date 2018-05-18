package com.example.student.pedometerx;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DeleteAchievementDialog extends AppCompatDialogFragment {
    DBclass db;
    public Button btndelete;
    TextView tvconfirm;
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        db=new DBclass(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.delete_achievement_dialog,null);
        btndelete = (Button) view.findViewById(R.id.btndeleteach);
        tvconfirm = (TextView) view.findViewById(R.id.txtconfirm);




        builder.setView(view)
                .setTitle("Delete Achievement");

        final AlertDialog show = builder.create();
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvconfirm.setVisibility(View.VISIBLE);
                btndelete.setVisibility(View.INVISIBLE);
                show.dismiss();
                db.deleteAchievement(SettingsFragment.Aid);
                Toast.makeText(getActivity(),"Achievement Deleted",Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new SettingsFragment()).commit();

            }

        });
        return show;
    }
}
