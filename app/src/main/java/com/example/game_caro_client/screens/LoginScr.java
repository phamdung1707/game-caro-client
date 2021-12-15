package com.example.game_caro_client.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.game_caro_client.R;
import com.example.game_caro_client.controllers.GameController;
import com.example.game_caro_client.dialogs.RegisterDialog;
import com.example.game_caro_client.dialogs.GameDialog;
import com.example.game_caro_client.models.GameSound;
import com.example.game_caro_client.services.GameService;

import java.util.regex.Pattern;

public class LoginScr extends AppCompatActivity {

    EditText txt_username;
    EditText txt_password;
    Button btn_login;
    Button btn_request_password;
    TextView txt_register;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_scr);

        GameController.gI().loginScr = this;
        GameController.levelScr = 1;
        context = this;

        GameController.gI().context = this.context;

        txt_username = (EditText) findViewById(R.id.txt_username);
        txt_password = (EditText) findViewById(R.id.txt_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_request_password = (Button) findViewById(R.id.btn_request_password);
        txt_register = (TextView) findViewById(R.id.txt_register);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-] ");
                String username_login = txt_username.getText().toString().toLowerCase();
                String password_login = txt_password.getText().toString().toLowerCase();
                if (username_login.length() < 5 || username_login.length() > 10 || special.matcher(username_login).find()) {
                    startOkDlg("Tài khoản phải từ 5 đến 10 ký tự và chỉ gồm chữ và số!");
                } else if (password_login.length() < 5 || password_login.length() > 20 || special.matcher(password_login).find()) {
                    startOkDlg("Mật khẩu phải từ 5 đến 20 ký tự và chỉ gồm chữ và số!");
                } else {
                    GameService.gI().login(username_login, password_login);
                }
            }
        });

        txt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterDialog.gI().start(context);
            }
        });

        btn_request_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startOkDlg("Vui lòng liên hệ Admin để lấy lại mật khẩu");
            }
        });

        txt_username.setText("kerhoangde");
        txt_password.setText("dungdeptrai");
    }

    public void nextScreen() {
        startActivity(new Intent(this, HomeScr.class));
        finish();
    }

    public void startOkDlg(String message) {
        GameDialog.gI().startOkDlg(context, message);
    }

}