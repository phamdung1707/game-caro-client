package com.example.game_caro_client.hubs;

import com.example.game_caro_client.controllers.GameController;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;

public class GameHub {
    public HubConnection hubConnection;
    private final String URL_SERVER = "https://game.dungpham.com.vn/game";
    private final String URL_LOCAL_HOST = "https://424c-2405-4803-fe69-98b0-3df5-d0cb-6487-b5c5.ngrok.io/game";
    public static GameHub instance;

    public static GameHub gI() {
        if (instance == null) {
            instance = new GameHub();
        }
        return instance;
    }

    public void onCreate() {
        hubConnection = HubConnectionBuilder.create(URL_SERVER).build();
        //hubConnection = HubConnectionBuilder.create(URL_LOCAL_HOST).build();

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
