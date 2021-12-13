package com.example.game_caro_client.models;

import java.util.ArrayList;
import java.util.List;

public class Room {
    public static int roomId;
    public static String data;
    public static int type;
    public static int money;
    public static boolean isStarted;
    public static boolean isReady;
    public static int time;
    public static Player player;
    public static Long hostId;
    public static Long turnId;
    public static int lastIndexSelected = -1;
    public static boolean isEnd;
    public static List<Integer> indexWins = new ArrayList<>();
    public static boolean isMeWin;
    public static int dameWin;
    public static boolean isPlayWithBot;

    public static void reset() {
        isStarted = false;
        isReady = false;
        time = 30;
        lastIndexSelected = -1;
        isEnd = false;
        indexWins.clear();
        if (Room.isPlayWithBot) {
            isReady = true;
        }
    }

    public static void resetData() {
        if (type == 0) {
            data = "000000000";
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();

        for (int i =0; i < 80; i++) {
            stringBuilder.append(0);
        }

        data = stringBuilder.toString();
    }
}
