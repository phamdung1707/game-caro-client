package com.example.game_caro_client.models;

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

    public static void reset() {
        isStarted = false;
        isReady = false;
        time = 30;
    }
}
