package com.example.game_caro_client.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.game_caro_client.R;
import com.example.game_caro_client.adapters.ItemGameAdapter;
import com.example.game_caro_client.controllers.GameController;
import com.example.game_caro_client.dialogs.GameDialog;
import com.example.game_caro_client.models.ItemGame;
import com.example.game_caro_client.models.Player;
import com.example.game_caro_client.models.Room;

import java.util.ArrayList;
import java.util.List;

public class GameScr extends AppCompatActivity {
    GridView grid_game;

    ArrayList<ItemGame> itemGames = new ArrayList<>();

    ItemGameAdapter itemGameAdapter;

    ImageButton btn_chat_public;

    ImageButton btn_out_game_scr;

    ImageButton btn_setting_game_scr;

    public static boolean isChangeUI;

    Handler handler = new Handler();

    Context context;

    TextView txt_username_game_scr_player_one;

    TextView txt_username_game_scr_player_two;

    TextView txt_money_game_scr;

    TextView txt_time_game_scr;

    long lastTimeGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_scr);

        this.context = this;

        GameController.gI().gameScr = this;

        grid_game = (GridView) findViewById(R.id.grid_game);
        btn_chat_public = (ImageButton) findViewById(R.id.btn_chat_public);
        btn_out_game_scr = (ImageButton) findViewById(R.id.btn_out_game_scr);
        btn_setting_game_scr = (ImageButton) findViewById(R.id.btn_setting_game_scr);
        txt_username_game_scr_player_one = (TextView) findViewById(R.id.txt_username_game_scr_player_one);
        txt_username_game_scr_player_two = (TextView) findViewById(R.id.txt_username_game_scr_player_two);
        txt_money_game_scr = (TextView) findViewById(R.id.txt_money_game_scr);
        txt_time_game_scr = (TextView) findViewById(R.id.txt_time_game_scr);

        while (itemGames.size() < 9) {
            itemGames.add(new ItemGame());
        }
        itemGameAdapter = new ItemGameAdapter(this, 0, itemGames);

        grid_game.setAdapter(itemGameAdapter);

        grid_game.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                itemGames.get(i).data = 1;
                isChangeUI = true;
            }
        });

        btn_chat_public.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameDialog.gI().startDlgChatRoom(context);
            }
        });

        btn_setting_game_scr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameDialog.gI().startDlgSettingRoom(context);
            }
        });

        Thread thread = new Thread() {
            @Override
            public void run() {
                for (;;) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            update();
                        }
                    });
                    try {
                        Thread.sleep(1L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        new Thread(thread).start();
    }

    public void update() {
        updateUI();

        if (Room.hostId == Player.getMyPlayer().id) {
            txt_username_game_scr_player_one.setText(Player.getMyPlayer().username);
            if (Room.player != null) {
                txt_username_game_scr_player_two.setText(Room.player.username);
            }
            else {
                txt_username_game_scr_player_two.setText("");
            }
        }
        else {
            txt_username_game_scr_player_two.setText(Player.getMyPlayer().username);
            if (Room.player != null) {
                txt_username_game_scr_player_one.setText(Room.player.username);
            }
            else {
                txt_username_game_scr_player_one.setText("");
            }
        }

        txt_money_game_scr.setText(Room.money + "$");
        txt_time_game_scr.setText(Room.time + "s");


        if (Room.isStarted) {
            if (Room.time > 0 && System.currentTimeMillis() - lastTimeGame > 1000L) {

                lastTimeGame = System.currentTimeMillis();
                Room.time--;

                if (Room.time <= 0) {
                    Room.time = 0;
                }
            }
        }
        else {
            Room.time = 30;
            lastTimeGame = System.currentTimeMillis();
        }
    }

    public void updateUI() {
        itemGameAdapter.update(itemGames);
    }

    public void nextScreen() {
        //gameLoop.destroy();
        startActivity(new Intent(this, HomeScr.class));
        finish();
    }
}