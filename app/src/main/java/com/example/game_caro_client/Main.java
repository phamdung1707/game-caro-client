package com.example.game_caro_client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.game_caro_client.hubs.GameHub;
import com.example.game_caro_client.screens.LoginScr;

public class Main extends AppCompatActivity {

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_scr);

        try {
            GameHub.gI().onCreate();
        }
        catch (Exception exception) {
            System.out.println("Disconnect to server");
        }


        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000L);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            nextScreen();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(thread).start();

    }

    public void nextScreen() {
        startActivity(new Intent(this, LoginScr.class));
        finish();
    }
}