package com.example.game_caro_client.hubs;

import com.example.game_caro_client.controllers.GameController;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;

public class GameHub {
    public HubConnection hubConnection;
    private String URL_SERVER = "https://game.dungpham.com.vn/game";
    public static GameHub instance;

    public static GameHub gI() {
        if (instance == null) {
            instance = new GameHub();
        }
        return instance;
    }

    public void onCreate() {
        hubConnection = HubConnectionBuilder.create(URL_SERVER).build();

        hubConnection.start().blockingAwait();

        hubConnection.on("ReceiveMessage", (id, message) -> {
            System.out.println("Message id: " + id + " - data: " + message);
            GameController.gI().onMessage(new Message(Integer.parseInt(id), message));
        }, String.class, String.class);
    }

    public void sendData(int id, String message) {
        hubConnection.send("SendMessage", Integer.toString(id), message);
    }
}
