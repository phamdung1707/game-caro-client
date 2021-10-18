package com.example.game_caro_client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.game_caro_client.hubs.GameHub;
import com.example.game_caro_client.screens.LoginScr;
import com.microsoft.signalr.HubConnectionState;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GameHub.gI().onCreate();

        NextScreen();
    }

    public void NextScreen() {
        startActivity(new Intent(this, LoginScr.class));
        finish();
    }
}