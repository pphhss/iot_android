package com.example.pharmacist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public class ConsentTypeZeroActivity extends AppCompatActivity {

    private final static String URL = "http://192.168.219.165:3000/users/reactType0";
    private String name;
    private String temperature;
    private String heartrate;
    private String symptominfo;
    private String curdisease;
    private String sid;

    private TextView tv_name;
    private TextView tv_temperature;
    private TextView tv_heartrate;
    private TextView tv_symptominfo;
    private TextView tv_curdisease;

    private RadioGroup rg_list;

    private Button btn_prescription;

    private Context me = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consenttypezero);

        Intent mi = getIntent();

        settings(mi);


        btn_prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton rb = (RadioButton)rg_list.findViewById(rg_list.getCheckedRadioButtonId());
                String select = rb.getText().toString();

                sendResult(select,sid);

                finish();


                };
            });
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
        sid = mi.getStringExtra("sid");

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

    private void sendResult(final String result,final String sid) {
        StringRequest sr = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, null){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();

                params.put("result",result);
                params.put("sid",sid);
                return params;
            }


         };
          RequestQueue queue = Volley.newRequestQueue(me);
          queue.add(sr);



    }
}
