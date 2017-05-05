package com.example.z.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ScrollingActivity extends AppCompatActivity {

    TextView temperatureText;
    TextView humidityText;
    String AQI = "";
    ImageView firstImg;
    TextView aqiTextView;
    bianliang bianliang =new bianliang();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        final CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(
                R.id.toolbar_layout);
        collapsingToolbar.setTitleEnabled(false);
        sendRequest();//执行json数据获取

        ArrayList<Fragment> list = new ArrayList<Fragment>();
        TabLayout mTabLayout = (TabLayout)findViewById(R.id.id_tab);
        ViewPager mViewPager = (ViewPager)findViewById(R.id.id_viewpager);
        Fragment1  fragment1 = new Fragment1();
        Fragment2  fragment2 = new Fragment2();
        Fragment3  fragment3 = new Fragment3();
        list.add(fragment1);
        list.add(fragment2);
        list.add(fragment3);
        AdapterTest adapterTest = new AdapterTest(getSupportFragmentManager(),list);
        mViewPager.setAdapter(adapterTest);
        mTabLayout.setupWithViewPager(mViewPager);



        firstImg = (ImageView) findViewById(R.id.imageview);
        temperatureText= (TextView) findViewById(R.id.temperatureText);
        humidityText = (TextView) findViewById(R.id.textView14);
        aqiTextView = (TextView) findViewById(R.id.aqitextView);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }


    //请求json数据
    private void sendRequest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    while (true){
                        Request request = new Request.Builder()
                                .url("https://api.thingspeak.com/channels/257486/feed/last.json?api_key=VDDOB5ZPW751BFE5")
                                .build();
                        Response response = client.newCall(request).execute();
                        String data = response.body().string();
                        Jsonobjecttest(data);
                        request = new Request.Builder()
                                .url("http://www.pm25.in/api/querys/aqis_by_station.json?station_code=1007A&token=5j1znBVAsnSf5xQyNQyq")
                                .build();
                        Response response1 = client.newCall(request).execute();
                        String data1 = response1.body().string();
                        Jsonobjecttest2(data1);
                        Thread.sleep(2);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    //接收json数据并解析
    private void Jsonobjecttest(String data) {
        try {
            JSONObject json = new JSONObject(data);
            bianliang.setTemperature(json.getString("field1")+"°");
            bianliang.setHumidity(json.getString("field2")+"%");
            bianliang.setPM2(json.getString("field3"));
            bianliang.setPM10(json.getString("field4"));
                showResponse();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //接收json数据并解析
    private void Jsonobjecttest2(String data1) {
        try {
            JSONArray json = new JSONArray(data1);
            for (int i=0;i<json.length();i++){
                JSONObject jsonObject = json.getJSONObject(i);
                bianliang.setCO(jsonObject.getString("co"));
                bianliang.setNO2(jsonObject.getString("no2"));
//                bianliang.setPM2(jsonObject.getString("pm2_5"));
//                bianliang.setPM10(jsonObject.getString("pm10"));
                bianliang.setO3(jsonObject.getString("o3"));
                bianliang.setSO2(jsonObject.getString("so2"));
                AQI = jsonObject.getString("aqi");
            }

            showResponse();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //子线程用于更新天气数据
    private void showResponse() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                temperatureText.setText(bianliang.getTemperature());
                humidityText.setText(bianliang.getHumidity());
                int i = 500;
                if (i>300){
                    int resourceId = R.mipmap.day_fog;
                    firstImg.setImageResource(resourceId);
                }
                aqiTextView.setText(AQI);


//                co.setText(CO);
//                no2.setText(NO2);
//                pm2.setText(PM2);
//                pm10.setText(PM10);
//                o3.setText(O3);
//                so2.setText(SO2);
            }
        });
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
