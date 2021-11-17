package com.example.game_caro_client.services;

import com.example.game_caro_client.hubs.GameHub;
import com.example.game_caro_client.hubs.Message;
import com.example.game_caro_client.models.Player;

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

    public void requestPassword(String username) {
        Message message = new Message(2);
        try {
            message.write(username);
            this.SendMessageToServer(message);
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            message = null;
        }
    }

    public void createRoom(int money, int type) {
        Message message = new Message(3);
        try {
            message.write(String.valueOf(money));
            message.write(String.valueOf(type));
            message.write(Player.getMyPlayer().id.toString());
            this.SendMessageToServer(message);
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            message = null;
        }
    }

    public void joinRoom(int roomId) {
        Message message = new Message(4);
        try {
            message.write(String.valueOf(roomId));
            message.write(Player.getMyPlayer().id.toString());
            this.SendMessageToServer(message);
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            message = null;
        }
    }

    public void readyRoom(int roomId) {
        Message message = new Message(5);
        try {
            message.write(String.valueOf(roomId));
            this.SendMessageToServer(message);
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            message = null;
        }
    }

    public void startRoom(int roomId) {
        Message message = new Message(6);
        try {
            message.write(String.valueOf(roomId));
            this.SendMessageToServer(message);
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            message = null;
        }
    }



}
