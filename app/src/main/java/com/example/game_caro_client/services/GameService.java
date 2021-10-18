package com.example.game_caro_client.services;

import com.example.game_caro_client.hubs.GameHub;
import com.example.game_caro_client.hubs.Message;

public class GameService {
    public static GameService instance;

    public static GameService gI() {
        if (instance == null) {
            instance = new GameService();
        }
        return instance;
    }

    public void SendMessageToServer(Message message) {
        GameHub.gI().sendData(message.getId(), message.getData());
    }

    public void login(String username, String password) {
        Message message = new Message(0);
        try {
            message.write(username);
            message.write(password);
            this.SendMessageToServer(message);
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            message = null;
        }
    }

    public void register(String username, String password) {
        Message message = new Message(1);
        try {
            message.write(username);
            message.write(password);
            this.SendMessageToServer(message);
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            message = null;
        }
    }
}
