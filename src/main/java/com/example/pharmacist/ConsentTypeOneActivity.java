package com.example.pharmacist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

public class ConsentTypeOneActivity extends AppCompatActivity {

    private static final String url  = "http://192.168.219.165:3000/users/reactType1";

    private String name;
    private String result;
    private String imageurl;
    private String sid;

    private TextView tv_name;
    private TextView tv_result;

    private ImageView iv_image;

    private Button btn_true;
    private Button btn_false;

    private Context me = this;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consent_type_one);
        Intent intent = getIntent();

        settings(intent);

        View.OnClickListener cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.equals(btn_true)) {
                    sendResult("1",sid);
                    finish();
                }else if(v.equals(btn_false)){
                    sendResult("0",sid);
                    finish();
                }
            }
        };
        btn_true.setOnClickListener(cl);
        btn_false.setOnClickListener(cl);

    }
    private void sendResult(final String result,final String sid) {
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
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
    private void settings(Intent intent){
        name = intent.getStringExtra("name");
        result = intent.getStringExtra("result");
        imageurl = intent.getStringExtra("imageurl");
        sid = intent.getStringExtra("sid");

        tv_name = (TextView)findViewById(R.id.type1_name);
        tv_result = (TextView)findViewById(R.id.type1_result);
        iv_image = (ImageView)findViewById(R.id.type1_image);
        btn_true = (Button)findViewById(R.id.type1_true);
        btn_false = (Button)findViewById(R.id.type1_false);

        tv_name.setText(name);
        tv_result.setText(result);

        final Handler handler = new Handler();
        Runnable r = new Runnable() {

            @Override
            public void run() {
                final Bitmap image = getBitmapFromURL(imageurl);
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        iv_image.setImageBitmap(image);
                    }
                });
            }
        };
        new Thread(r).start();



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
        }finally {

        }
    }


}
