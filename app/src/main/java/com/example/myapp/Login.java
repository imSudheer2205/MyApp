package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    /*EditText editTextEmail,editTextPassword;
    Button BtnLogin;
    ProgressDialog pDialog;

    //JSONPaser jsonParser = new JSONParser();
    //private static final String LOGIN_URL = "http://yourdomain.com/login.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*editTextEmail = (EditText)findViewById(R.id.editText_email);
        editTextPassword = (EditText)findViewById(R.id.editText_password);
        BtnLogin = (Button)findViewById(R.id.btn_Login);
        BtnLogin.setOnClickListener(this);*/

    }
    //@Override
    /*public void onClick(View v) {
        switch (v.getId()) {
            //case R.id.btn_Login: new AttemptLogin().execute();
            // here we have used, switch case, because on login activity you may //also want to show registration button, so if the user is new ! we can go the //registration activity , other than this we could also do this without switch //case.
            default:
                break;


        }
    }*/

}
