package com.mrhi2020.ex60notification;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //제목글씨 변경
        getSupportActionBar().setTitle("Second Activity");
    }
}