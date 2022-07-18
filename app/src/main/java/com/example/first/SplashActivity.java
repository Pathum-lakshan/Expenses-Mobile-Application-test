package com.example.first;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class SplashActivity extends AppCompatActivity {
    private Handler handler;
    private boolean network;
    private boolean first;
    private ProgressBar progressBar;
    private int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler = new Handler(Looper.myLooper());
        progressBar=findViewById(R.id.progressBar);
        progressBar();
    }

    public void progressBar(){
        i=progressBar.getProgress();
        new Thread(new Runnable() {
            @Override
            public void run() {
                network=true;
                first=true;
                while (network){
                    if (isInternetAvailable() && isNetworkAvailable(SplashActivity.this)){

                        if (first){
                            loadLoginUi();
                            network =false;
                        }else {
                            network =false;

                            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);

                            startActivity(intent);
                            finish();
                        }



                    }else {

                        progressBar.setProgress(i);
                        i++;
                    }
                    first=false;
                }

            }
        }).start();
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }


    public boolean isInternetAvailable() {
        try {
            InetAddress address = InetAddress.getByName("www.google.com");
            return !address.equals("");
        } catch (UnknownHostException e) {
            // Log error
        }
        return false;
    }

 private void loadLoginUi() {



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