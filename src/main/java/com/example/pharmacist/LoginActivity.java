package com.example.pharmacist;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONObject;

import java.util.concurrent.Executor;

public class LoginActivity extends AppCompatActivity {

    Context object = this;
    EditText ed_userID;
    EditText ed_userPW;
    Button btn_login;
    String newToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed_userID = (EditText)findViewById(R.id.userID);
        ed_userPW = (EditText)findViewById(R.id.userPW);
        btn_login = (Button)findViewById(R.id.login);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                newToken = instanceIdResult.getToken();
                Log.d("hello","Success Token  : "+newToken);
            }
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("phs",response);

                        if(response.equals("TRUE")){
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(object,"아이디 비밀번호 불일치",Toast.LENGTH_SHORT).show();
                        }

                    }
                };

                LoginRequest lr = new LoginRequest(ed_userID.getText().toString(),ed_userPW.getText().toString(),newToken,listener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(lr);
            }
        });
        }
        }
