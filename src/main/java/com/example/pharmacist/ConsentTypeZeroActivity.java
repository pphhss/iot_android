package com.example.pharmacist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class ConsentTypeZeroActivity extends AppCompatActivity {

    String name;
    String temperature;
    String heartrate;
    String symptominfo;
    String curdisease;

    TextView tv_name;
    TextView tv_temperature;
    TextView tv_heartrate;
    TextView tv_symptominfo;
    TextView tv_curdisease;

    RadioGroup rg_list;

    Button btn_prescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consenttypezero);

        Intent mi = getIntent();

        settings(mi);







    }

    private Bitmap getBitmapFromURL(String src) {
        try {
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void settings(Intent mi){
        name = mi.getStringExtra("name");
        temperature = mi.getStringExtra("temperature");
        heartrate = mi.getStringExtra("heartrate");
        symptominfo = mi.getStringExtra("symptominfo");
        curdisease = mi.getStringExtra("curdisease");

        tv_name = (TextView)findViewById(R.id.type0_name);
        tv_curdisease = (TextView)findViewById(R.id.type0_curdisease);
        tv_heartrate = (TextView)findViewById(R.id.type0_heartrate);
        tv_symptominfo = (TextView)findViewById(R.id.type0_symptominfo);
        tv_temperature = (TextView)findViewById(R.id.type0_temperature);

        rg_list = (RadioGroup)findViewById(R.id.type0_radio);

        btn_prescription = (Button) findViewById(R.id.type0_prescription);

        tv_temperature.setText(temperature);
        tv_symptominfo.setText(symptominfo);
        tv_heartrate.setText(heartrate);
        tv_curdisease.setText(curdisease);
        tv_name.setText(name);

        String[] list_drug = getResources().getStringArray(R.array.list_drug);

        for(String drug : list_drug){
            RadioButton rb = new RadioButton(this);
            rb.setText(drug);
            rg_list.addView(rb);
        }
    }


}
