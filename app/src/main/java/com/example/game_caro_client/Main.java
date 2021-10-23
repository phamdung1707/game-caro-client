package com.example.game_caro_client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.example.game_caro_client.hubs.GameHub;
import com.example.game_caro_client.screens.LoginScr;
import com.microsoft.signalr.HubConnectionState;

public class Main extends AppCompatActivity {

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GameHub.gI().onCreate();

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000L);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            NextScreen();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(thread).start();

    }

    public void NextScreen() {
        startActivity(new Intent(this, LoginScr.class));
        finish();
    }
}