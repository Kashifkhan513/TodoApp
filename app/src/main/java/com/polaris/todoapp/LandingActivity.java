package com.polaris.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LandingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
    }

    public void sinUp(View view) {
        startActivity(new Intent(LandingActivity.this,RegisterActivity.class));

    }

    public void logIn(View view) {
        startActivity(new Intent(LandingActivity.this,LoginActivity.class));

    }
}
