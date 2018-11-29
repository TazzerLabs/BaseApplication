package com.example.haleypsheridan.navigationdrawer;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ReferenceFragment extends Fragment implements View.OnClickListener{

    Dialog myDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        // create myDialog
        myDialog = new Dialog(getActivity());

        View v = inflater.inflate(R.layout.fragment_reference, container, false);

        Button b1 = (Button) v.findViewById(R.id.Button1);
        b1.setOnClickListener(this);
        Button b2 = (Button) v.findViewById(R.id.Button2);
        b2.setOnClickListener(this);
        Button b3 = (Button) v.findViewById(R.id.Button3);
        b3.setOnClickListener(this);
        Button b4 = (Button) v.findViewById(R.id.Button4);
        b4.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
    TextView txtclose;

        switch(v.getId()){
            case R.id.Button1:
                myDialog.setContentView(R.layout.haley);
                break;
            case R.id.Button2:
                myDialog.setContentView(R.layout.grant);
                break;
            case R.id.Button3:
                myDialog.setContentView(R.layout.john);
                break;
            case R.id.Button4:
                myDialog.setContentView(R.layout.elliot);
                break;

        }
        txtclose = (TextView) myDialog.findViewById(R.id.txtclose);

        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

}