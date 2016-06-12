package com.example.administrator.dubbleprocess;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.dubbleprocess.service.MainService;
import com.example.administrator.dubbleprocess.service.SecondService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.startService(new Intent(this, MainService.class));
        this.startService(new Intent(this, SecondService.class));
    }
}
