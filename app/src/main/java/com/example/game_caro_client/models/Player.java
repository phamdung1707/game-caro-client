package com.example.game_caro_client.models;

public class Player {
    public Long id;
    public String username;
    public static Player myPlayer;

    public Player() {

    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public static Player getMyPlayer() {
        return myPlayer;
    }
}
