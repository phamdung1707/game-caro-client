package com.example.game_caro_client.controllers;

import android.content.Context;
import android.os.Handler;

import com.example.game_caro_client.dialogs.GameDialog;
import com.example.game_caro_client.hubs.Message;
import com.example.game_caro_client.models.Player;
import com.example.game_caro_client.models.Room;
import com.example.game_caro_client.models.TextChat;
import com.example.game_caro_client.screens.GameScr;
import com.example.game_caro_client.screens.HomeScr;
import com.example.game_caro_client.screens.LoginScr;

public class GameController {
    public static GameController instance;

    public LoginScr loginScr;

    public HomeScr homeScr;

    public GameScr gameScr;

    public Context context;

    public static int levelScr;

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
                Room.isPlayWithBot = false;

                System.out.println("create room: " + Room.roomId);

                homeScr.nextScreen();
                break;
            case 4:
                System.out.println("join room");

                if (message.data.startsWith("0")) {
                    homeScr.startOkDlg(message.data.substring(2));
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
                    if (Room.isStarted) {
                        message.data = message.data.substring(2);
                        Player.getMyPlayer().money = message.ReadLong();
                        Player.getMyPlayer().countWin = message.ReadInt();
                    }

                    if (!Room.hostId.equals(Player.getMyPlayer().id)) {
                        if (Room.isStarted) {
                            gameScr.startOkDlgWithAction("Chủ phòng đã thoát. Bạn nhận được "+ ((int)(Room.money * 1.8)) + "$");
                        } else {
                            gameScr.startOkDlgWithAction("Chủ phòng đã thoát.");
                        }
                    }
                    else {
                        if (Room.isStarted) {
                            gameScr.startOkDlg("Đối thủ đã rời phòng. Bạn nhận được "+ ((int)(Room.money * 1.8)) + "$");
                        } else {
                            gameScr.startOkDlg("Đối thủ đã rời phòng.");
                        }
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
                Room.reset();
                break;
            case 7:
                System.out.println("ready room");
                Room.isReady = message.ReadString().equals("1");
                break;
            case 8:
                System.out.println("start room");
                Room.turnId = message.ReadLong();
                Room.data = message.ReadString();
                if (Room.hostId.equals(Player.getMyPlayer().id)) {
                    Player.getMyPlayer().money = message.ReadLong();
                    Player.getMyPlayer().countGame = message.ReadInt();
                    Room.player.money = message.ReadLong();
                    Room.player.countGame = message.ReadInt();
                }
                else {
                    Room.player.money = message.ReadLong();
                    Room.player.countGame = message.ReadInt();
                    Player.getMyPlayer().money = message.ReadLong();
                    Player.getMyPlayer().countGame = message.ReadInt();
                }
                GameScr.isChangeUI = true;
                Room.isStarted = true;
                break;
            case 9:
                System.out.println("attack room");
                int id = message.ReadInt();
                Room.data = message.ReadString();
                Room.turnId = message.ReadLong();
                Room.lastIndexSelected = -1;
                Room.time = 30;
                GameScr.isChangeUI = true;
                String dameWin = message.ReadString();
                if (!dameWin.equals("0")) {
                    String[] array = dameWin.split("\\-");

                    Room.indexWins.clear();
                    Room.dameWin = Integer.parseInt(array[0]);

                    for (int i = 1; i < array.length; i++) {
                        Room.indexWins.add(Integer.parseInt(array[i]));
                    }

                    if (dameWin.startsWith(GameScr.getDame(Room.hostId, Player.getMyPlayer().id) + "")) {
                        Player.getMyPlayer().money = message.ReadLong();
                        Player.getMyPlayer().countWin = message.ReadInt();
                        //gameScr.startOkDlg("Bạn đã thắng và nhận được " + ((int)(Room.money * 1.8)) + "$");
                        Room.isMeWin = true;
                    }
                    else {
                        Room.player.money = message.ReadLong();
                        Room.player.countWin = message.ReadInt();
                        //gameScr.startOkDlg("Bạn đã thua!");
                        Room.isMeWin = false;
                    }

                    Room.isEnd = true;
                }
                break;
            case 10:
                System.out.println("set money room");
                Room.money = message.ReadInt();
                break;
            case 11:
                System.out.println("chat room");
                int roomId = message.ReadInt();
                GameScr.textChats.add(new TextChat(message.ReadString(), false));
                break;
            case 12:
                System.out.println("chat global");
                if (levelScr == 3) {
                     GameScr.textChats.add(new TextChat(message.ReadString(), true));
                }
                if (levelScr == 2) {
                    HomeScr.textChatGlobals.add(new TextChat(message.ReadString(), true));
                }
                break;
            case 13:
                System.out.println("create room bot");

                Room.roomId = message.ReadInt();
                Room.hostId = Player.getMyPlayer().id;
                Room.money = 1000;
                Room.type = 1;
                Room.data = message.ReadString();
                Room.isReady = true;
                Room.player = new Player();
                Room.player.id = -2L;
                Room.player.username = "bot [" + Room.roomId + "]";
                Room.player.money = 0L;
                Room.isStarted = false;
                Room.time = 30;
                Room.isPlayWithBot = true;

                homeScr.nextScreen();
                break;
            case 14:
                System.out.println("start room bot");
                Room.data = message.ReadString();
                Player.getMyPlayer().countGame = message.ReadInt();
                GameScr.isChangeUI = true;
                Room.isStarted = true;
                Room.turnId = Player.getMyPlayer().id;
                break;
            case 15:
                System.out.println("attack bot");
                Room.time = 30;
                Room.data = message.ReadString();
                Room.turnId = Player.getMyPlayer().id;
                Room.lastIndexSelected = -1;
                GameScr.isChangeUI = true;
                String dameWinBot = message.ReadString();
                if (!dameWinBot.equals("0")) {
                    String[] array = dameWinBot.split("\\-");

                    Room.indexWins.clear();
                    Room.dameWin = Integer.parseInt(array[0]);

                    for (int i = 1; i < array.length; i++) {
                        Room.indexWins.add(Integer.parseInt(array[i]));
                    }

                    if (dameWinBot.startsWith(GameScr.getDame(Room.hostId, Player.getMyPlayer().id) + "")) {
                        Player.getMyPlayer().money = message.ReadLong();
                        Player.getMyPlayer().countWin = message.ReadInt();
                        GameScr.isChangeUI = true;
                        Room.isMeWin = true;
                    }
                    else {
                        Room.isMeWin = false;
                    }

                    Room.isEnd = true;
                }
                break;
            case 16:
                System.out.println("out room bot");
                Room.reset();
                break;
        }
    }


}
