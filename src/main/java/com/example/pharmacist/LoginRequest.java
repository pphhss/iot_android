package com.example.pharmacist;


import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest{

    private final static String url ="http://192.168.219.165:3000/users/loginAction";
    private Map<String,String> parameters;


    public LoginRequest(String userID, String userPW,String token, Response.Listener<String> listener) {
        super(Method.POST,url,listener,null);

        parameters = new HashMap<>();
        Log.d("phs",token);
        parameters.put("userID",userID);
        parameters.put("userPW",userPW);
        parameters.put("token",token);
    }



    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
