package com.polaris.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class Splashctivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences=getSharedPreferences("user_info",MODE_PRIVATE);
                boolean status=sharedPreferences.getBoolean("LOGGED_IN",false);
                if (status)
                {
                    startActivity(new Intent(Splashctivity.this,MainActivity.class));
                    finish();
                }
                else {
                    startActivity(new Intent(Splashctivity.this,LandingActivity.class));
                    finish();
                }



            }
        },2000);
    }
}
