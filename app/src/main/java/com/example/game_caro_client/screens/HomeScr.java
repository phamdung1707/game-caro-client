package com.example.game_caro_client.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.game_caro_client.R;
import com.example.game_caro_client.common.PlayerUtils;
import com.example.game_caro_client.controllers.GameController;
import com.example.game_caro_client.dialogs.GameDialog;
import com.example.game_caro_client.models.Player;

public class HomeScr extends AppCompatActivity {
    Button btn_create_room;

    Button btn_join_room;

    Button btn_bot_room;

    Context context;

    Handler handler = new Handler();

    Thread gameLoop;

    TextView txt_username_home_scr;

    TextView txt_money_home_scr;

    TextView txt_rate_home_scr;

    boolean isUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_scr);

        context = this;

        GameController.gI().homeScr = this;

        btn_create_room = (Button) findViewById(R.id.btn_create_room);
        btn_join_room = (Button) findViewById(R.id.btn_join_room);
        btn_bot_room = (Button) findViewById(R.id.btn_bot_room);
        txt_username_home_scr = (TextView) findViewById(R.id.txt_username_home_scr);
        txt_money_home_scr = (TextView) findViewById(R.id.txt_money_home_scr);
        txt_rate_home_scr = (TextView) findViewById(R.id.txt_rate_home_scr);

        btn_create_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameDialog.gI().startDlgSelectMod(context);
            }
        });

        btn_join_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameDialog.gI().startDlgSelectRoom(context);
            }
        });

        btn_bot_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        isUpdate = true;

        gameLoop = new Thread() {
            @Override
            public void run() {
                while (isUpdate) {
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
        new Thread(gameLoop).start();
    }

    public void update() {
        txt_username_home_scr.setText(Player.getMyPlayer().username);
        txt_money_home_scr.setText(PlayerUtils.getMoneys(Player.getMyPlayer().money) + "$");
        txt_rate_home_scr.setText(PlayerUtils.getMoneys(Player.getMyPlayer().countWin) + "/" + PlayerUtils.getMoneys(Player.getMyPlayer().countGame));
    }

    public void nextScreen() {
        isUpdate = false;
        //gameLoop.destroy();
        startActivity(new Intent(this, GameScr.class));
        finish();
    }

}