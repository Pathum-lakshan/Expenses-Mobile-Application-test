package com.example.first;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        loadLoginUi();
    }

    private void loadLoginUi() {


        handler = new Handler(Looper.myLooper());
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);

                        startActivity(intent);
                        finish();


                    }
                });

            }


        });
        thread.start();

    }
}