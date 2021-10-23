package com.example.game_caro_client.controllers;

import android.content.Context;
import android.os.Handler;

import com.example.game_caro_client.dialogs.GameDialog;
import com.example.game_caro_client.hubs.Message;
import com.example.game_caro_client.models.Player;
import com.example.game_caro_client.screens.LoginScr;

public class GameController {
    public static GameController instance;

    public LoginScr loginScr;

    public Context context;

    Handler handler = new Handler();

    public static GameController gI() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    public void onMessage(Message message) {
        switch (message.id) {
            case 0:
                System.out.println("login");
                if (message.data.startsWith("0")) {

                    String messageLogin = message.data.substring(2);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            GameDialog.gI().startOkDlg(context, messageLogin);
                        }
                    });
                }
                else {
                    message.data = message.data.substring(2);
                    Player.myPlayer = new Player();
                    Player.getMyPlayer().id = message.ReadLong();
                    Player.getMyPlayer().username = message.ReadString();
                    loginScr.NextScreen();
                }
                break;
            case 1:
                System.out.println("register");
                break;
            case 2:
                System.out.println("request password");
                break;
        }
    }


}
