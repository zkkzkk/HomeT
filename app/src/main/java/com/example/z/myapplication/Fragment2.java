package com.example.z.myapplication;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Fragment2 extends Fragment {
    ToggleButton led1;
    ToggleButton led2;
    ToggleButton led3;

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.control_fragment2, container, false);


        led1 = (ToggleButton) view.findViewById(R.id.toggleButton);
        led2 = (ToggleButton) view.findViewById(R.id.toggleButton2);
        led3 = (ToggleButton) view.findViewById(R.id.toggleButton3);

        //三个灯的监听事件
        led1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    led1.setBackgroundResource(R.mipmap.woshikai);
                    new Background_get().execute("led1=1");
                } else {
                    led1.setBackgroundResource(R.mipmap.woshi);
                    new Background_get().execute("led1=0");
                }
            }
        });

        led2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    led2.setBackgroundResource(R.mipmap.ketkai);
                    new Background_get().execute("led2=1");
                } else {
                    led2.setBackgroundResource(R.mipmap.ketguan);
                    new Background_get().execute("led2=0");
                }
            }
        });

        led3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    led3.setBackgroundResource(R.mipmap.chufkai);
                    new Background_get().execute("led3=1");
                } else {
                    led3.setBackgroundResource(R.mipmap.chufguan);
                    new Background_get().execute("led3=0");
                }
            }
        });
        return view;

    }
    //传输控制信号
    private class Background_get extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL("http://homeautomation.s1.natapp.cc?" + params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null)
                    result.append(inputLine).append("\n");

                in.close();
                connection.disconnect();
                return result.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
