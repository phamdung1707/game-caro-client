package com.example.game_caro_client.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.game_caro_client.R;
import com.example.game_caro_client.controllers.GameController;
import com.example.game_caro_client.services.GameService;

public class LoginScr extends AppCompatActivity {

    EditText txt_username;
    EditText txt_password;
    Button btn_login;
    Button btn_request_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_scr);

        GameController.gI().loginScr = this;

        txt_username = (EditText) findViewById(R.id.txt_username);
        txt_password = (EditText) findViewById(R.id.txt_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_request_password = (Button) findViewById(R.id.btn_request_password);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameService.gI().login(txt_username.getText().toString(), txt_password.getText().toString());
            }
        });
    }

    public void NextScreen() {
        startActivity(new Intent(this, HomeScr.class));
        finish();
    }
}