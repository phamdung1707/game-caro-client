package com.example.game_caro_client.controllers;

import android.content.Context;
import android.os.Handler;

import com.example.game_caro_client.dialogs.GameDialog;
import com.example.game_caro_client.hubs.Message;
import com.example.game_caro_client.models.Player;
import com.example.game_caro_client.models.Room;
import com.example.game_caro_client.screens.GameScr;
import com.example.game_caro_client.screens.HomeScr;
import com.example.game_caro_client.screens.LoginScr;

import java.math.RoundingMode;

public class GameController {
    public static GameController instance;

    public LoginScr loginScr;

    public HomeScr homeScr;

    public GameScr gameScr;

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
                    Player.getMyPlayer().money = message.ReadLong();
                    Player.getMyPlayer().countWin = message.ReadInt();
                    Player.getMyPlayer().countGame = message.ReadInt();
                    loginScr.nextScreen();
                }
                break;

            case 1:
                System.out.println("register");

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        GameDialog.gI().startOkDlg(context, message.data.substring(2));
                    }
                });
                break;

            case 2:
                System.out.println("request password");
                break;

            case 3:
                System.out.println("create room");

                Room.roomId = message.ReadInt();
                Room.hostId = message.ReadLong();
                Room.money = message.ReadInt();
                Room.type = message.ReadInt();
                Room.data = message.ReadString();
                Room.isReady = false;
                Room.player = null;
                Room.isStarted = false;
                Room.time = 30;

                System.out.println("create room: " + Room.roomId);

                homeScr.nextScreen();
                break;
            case 4:
                if (message.data.startsWith("0")) {
                    System.out.println(message.data.substring(2));
                }
                else {
                    message.data = message.data.substring(2);
                    Room.roomId = message.ReadInt();
                    Room.hostId = message.ReadLong();
                    Room.money = message.ReadInt();
                    Room.type = message.ReadInt();
                    Room.data = message.ReadString();
                    Room.isReady = false;
                    Room.player = null;
                    Room.isStarted = false;
                    Room.time = 30;

                    homeScr.nextScreen();
                }
                break;
            case 5:
                if (message.data.startsWith("0")) {
                    Room.player = null;
                    Room.reset();
                    if (Room.roomId != Player.getMyPlayer().id) {
                        gameScr.nextScreen();
                    }
                    else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                GameDialog.gI().startOkDlg(context, "Đối thủ đã rời phòng");
                            }
                        });
                    }
                }
                else {
                    message.data = message.data.substring(2);
                    Room.player = new Player();
                    Room.player.id = message.ReadLong();
                    Room.player.username = message.ReadString();
                    Room.player.money = message.ReadLong();
                    Room.player.countWin = message.ReadInt();
                    Room.player.countGame = message.ReadInt();
                }
                break;
        }
    }


}
