package com.example.z.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Fragment1 extends Fragment {
    bianliang bianliang = new bianliang();
    TextView pm2, pm10, so2, no2, o3, co;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.data_fragment1, container, false);

        no2 = (TextView) view.findViewById(R.id.ntextVieo2w);
        no2.setText(bianliang.getNO2());


        String PPM = bianliang.getPM2();

        pm2 = (TextView) view.findViewById(R.id.pm2textView);
        pm2.setText(PPM);

        pm10 = (TextView) view.findViewById(R.id.pm10textView);
        pm10.setText(bianliang.getPM10());

        so2 = (TextView) view.findViewById(R.id.so2textView);
        so2.setText(bianliang.getSO2());

        o3 = (TextView) view.findViewById(R.id.o3textView);
        o3.setText(bianliang.getO3());

        co = (TextView) view.findViewById(R.id.cotextView);
        co.setText(bianliang.getCO());
        return view;
    }
}
