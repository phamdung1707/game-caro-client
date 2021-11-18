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
import android.widget.TextView;
import android.widget.Toast;

import com.example.game_caro_client.R;
import com.example.game_caro_client.adapters.ItemGameAdapter;
import com.example.game_caro_client.controllers.GameController;
import com.example.game_caro_client.dialogs.GameDialog;
import com.example.game_caro_client.models.ItemGame;
import com.example.game_caro_client.models.Player;
import com.example.game_caro_client.models.Room;
import com.example.game_caro_client.services.GameService;

import java.util.ArrayList;

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

    Button btn_status_game_scr;

    long lastTimeGame;

    public static boolean isShowDialogOk;

    public static String infoDialogOk;

    public static boolean isShowDialogOkWithAction;

    public static String infoDialogOkWithAction;

    public static boolean isShowDialogOkEndGame;

    public static String infoDialogOkEndGame;

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
        btn_status_game_scr = (Button) findViewById(R.id.btn_status_game_scr);

        while (itemGames.size() < 9) {
            itemGames.add(new ItemGame());
        }
        itemGameAdapter = new ItemGameAdapter(this, 0, itemGames);

        grid_game.setAdapter(itemGameAdapter);

        grid_game.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (Room.isStarted) {
                    if (Room.turnId.equals(Player.getMyPlayer().id)) {
                        int dame = getDame(Room.hostId, Room.turnId);
                        if (Room.type == 0) {
                            attackByTypeZero(dame, i);
                        }
                        else {
                            attackByTypeOne();
                        }

                    }
                    else {
                        Toast.makeText(context, "Vui lòng chờ đến lượt!", Toast.LENGTH_SHORT).show();
                    }
                }
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

        btn_out_game_scr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameDialog.gI().startYesNoDlg(context, "Bạn có muốn thoát phòng không?");
            }
        });

        btn_status_game_scr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Room.hostId.equals(Player.getMyPlayer().id)) {
                    Room.isStarted = true;
                    GameService.gI().startRoom();
                }
                else {
                    Room.isReady = true;
                    GameService.gI().readyRoom();
                }
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

    public void attackByTypeZero(int dame, int index) {
        String dataCheck = Room.data.substring(index, index + 1);

        if (!dataCheck.equals("0") && !dataCheck.equals(String.valueOf(dame))) {
            Toast.makeText(context, "Đối thủ đã chọn ô này!", Toast.LENGTH_SHORT).show();
            return;
        }

        int countDame = getCountDame(dame);

        if (countDame < 3) {
            if (dataCheck.equals(String.valueOf(dame))) {
                Toast.makeText(context, "Bạn đã chọn ô này rồi!", Toast.LENGTH_SHORT).show();
                return;
            }

            Room.lastIndexSelected = -1;
            attack(index, dame);
            GameService.gI().attack();
            isChangeUI = true;
            return;
        }

        if (Room.lastIndexSelected == -1 && dataCheck.equals("0")) {
            return;
        }

        if (Room.lastIndexSelected == -1 && dataCheck.equals(String.valueOf(dame))) {
            Room.lastIndexSelected = index;
            isChangeUI = true;
            return;
        }
        if (Room.lastIndexSelected == index) {
            Room.lastIndexSelected = -1;
        }
        else {
            attack(index, dame);
            GameService.gI().attack();
        }
        isChangeUI = true;
    }

    public void attackByTypeOne() {

    }

    public void attack(int indexAttack, int dame) {
        ArrayList<ItemGame> dataRoom = new ArrayList<>(itemGames);

        dataRoom.get(indexAttack).data = dame;

        if (Room.lastIndexSelected != -1) {
            dataRoom.get(Room.lastIndexSelected).data = 0;
        }

        StringBuilder data = new StringBuilder();
        for (int i = 0; i < dataRoom.size(); i++) {
            data.append(dataRoom.get(i).data);
        }

        Room.data = data.toString();
    }

    public int getCountDame(int dame) {
        int count = 0;
        for (int i = 0; i < itemGames.size(); i++) {
            if (itemGames.get(i).data == dame) {
                count++;
            }
        }
        return count;
    }

    public static int getDame(Long hostId, Long playerId) {
        return (hostId.equals(playerId)) ? 1: 2;
    }

    public void update() {
        if (Room.isStarted) {
            try {
                char[] charArray = Room.data.toCharArray();
                for (int i = 0; i < itemGames.size(); i++) {
                    itemGames.get(i).data = Integer.parseInt(charArray[i] + "");
                }
            }
            catch (Exception ignored)
            {
            }
        }

        updateUI();

        if (Room.isStarted || Room.player == null) {
            btn_status_game_scr.setVisibility(View.INVISIBLE);
        }
        else {
            if (Room.hostId.equals(Player.getMyPlayer().id)) {
                btn_status_game_scr.setText("Bắt đầu");
                if (!Room.isReady) {
                    btn_status_game_scr.setVisibility(View.INVISIBLE);
                }
                else {
                    btn_status_game_scr.setVisibility(View.VISIBLE);
                }
            }
            else {
                btn_status_game_scr.setText("Sẵn sàng");
                if (Room.isReady) {
                    btn_status_game_scr.setVisibility(View.INVISIBLE);
                }
                else {
                    btn_status_game_scr.setVisibility(View.VISIBLE);
                }
            }
        }

        if (Room.hostId.equals(Player.getMyPlayer().id)) {
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
                    Room.time = 30;
                }
            }
        }
        else {
            Room.time = 30;
            lastTimeGame = System.currentTimeMillis();
        }

        if (isShowDialogOk) {
            isShowDialogOk = false;
            GameDialog.gI().startOkDlg(context, infoDialogOk);
        }

        if (isShowDialogOkWithAction) {
            isShowDialogOkWithAction = false;
            GameDialog.gI().startOkDlgWithAction(context, infoDialogOkWithAction);
        }
    }

    public void updateUI() {
        itemGameAdapter.update(itemGames);
    }

    public void backScreen() {
        //gameLoop.destroy();
        startActivity(new Intent(this, HomeScr.class));
        finish();
    }

    public void startOkDlg(String info) {
        isShowDialogOk = true;
        infoDialogOk = info;
    }

    public void startOkDlgWithAction(String info) {
        isShowDialogOkWithAction = true;
        infoDialogOkWithAction = info;
    }

    public void startOkDlgOkEndGame(String info) {
        isShowDialogOkEndGame = true;
        infoDialogOkEndGame = info;
    }

}