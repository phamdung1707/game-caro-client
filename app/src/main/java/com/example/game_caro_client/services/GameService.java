package com.example.game_caro_client.services;

import com.example.game_caro_client.hubs.GameHub;
import com.example.game_caro_client.hubs.Message;
import com.example.game_caro_client.models.Player;
import com.example.game_caro_client.models.Room;

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

    public void exitRoom() {
        Message message = new Message(6);
        try {
            message.write(String.valueOf(Room.roomId));
            message.write(Player.getMyPlayer().id.toString());
            this.SendMessageToServer(message);
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            message = null;
        }
    }

    public void readyRoom() {
        Message message = new Message(7);
        try {
            message.write(String.valueOf(Room.roomId));
            this.SendMessageToServer(message);
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            message = null;
        }
    }

    public void startRoom() {
        Message message = new Message(8);
        try {
            message.write(String.valueOf(Room.roomId));
            this.SendMessageToServer(message);
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            message = null;
        }
    }

    public void attack() {
        Message message = new Message(9);
        try {
            message.write(String.valueOf(Room.roomId));
            message.write(Room.data);
            if (Room.hostId == Player.getMyPlayer().id) {
                Room.turnId = Room.player.id;
            }
            else {
                Room.turnId = Room.hostId;
            }
            message.write(String.valueOf(Room.turnId));
            this.SendMessageToServer(message);
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            message = null;
        }
    }

    public void setMoney(int money) {
        Message message = new Message(10);
        try {
            message.write(String.valueOf(Room.roomId));
            Room.money = money;
            message.write(String.valueOf(money));
            this.SendMessageToServer(message);
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            message = null;
        }
    }

    public void chat(String content) {
        Message message = new Message(11);
        try {
            message.write(String.valueOf(Room.roomId));
            message.write(Player.getMyPlayer().username);
            message.write(content);
            this.SendMessageToServer(message);
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            message = null;
        }
    }

    public void chatGlobal(String content) {
        Message message = new Message(12);
        try {
            message.write(Player.getMyPlayer().id.toString());
            message.write(Player.getMyPlayer().username);
            message.write(content);
            this.SendMessageToServer(message);
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            message = null;
        }
    }

}
