package com.example.game_caro_client.models;

public class TextChat {
    public String content;
    public boolean isChatServer;

    public TextChat(String content, boolean isChatServer) {
        this.content = content;
        this.isChatServer = isChatServer;
    }
}
