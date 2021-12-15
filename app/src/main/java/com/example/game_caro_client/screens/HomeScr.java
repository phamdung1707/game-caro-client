package com.example.game_caro_client.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.game_caro_client.R;
import com.example.game_caro_client.common.PlayerUtils;
import com.example.game_caro_client.controllers.GameController;
import com.example.game_caro_client.dialogs.GameDialog;
import com.example.game_caro_client.models.GameSound;
import com.example.game_caro_client.models.Player;
import com.example.game_caro_client.models.TextChat;
import com.example.game_caro_client.services.GameService;

import java.util.ArrayList;
import java.util.List;

public class HomeScr extends AppCompatActivity {
    Button btn_create_room;

    Button btn_join_room;

    Button btn_bot_room;

    Context context;

    public Handler handler = new Handler();

    Thread gameLoop;

    TextView txt_username_home_scr;

    TextView txt_money_home_scr;

    TextView txt_rate_home_scr;

    boolean isUpdate;

    public static boolean isShowDialogOK;

    public static String infoDialogOk;

    MediaPlayer mediaPlayer;

    ImageView btn_sound_setting_home_scr;

    public static List<TextChat> textChatGlobals = new ArrayList<>();

    RelativeLayout bgr_txt_chat_global;

    TextView txt_chat_global_home_scr;

    public static long lastTimeShowTextChat;

    public static int gameTicks;

    public int xChat;

    ImageButton btn_send_chat_global;

    EditText edit_text_chat_global_home_scr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_scr);

        context = this;

        GameController.gI().homeScr = this;
        GameController.levelScr = 2;

        btn_create_room = (Button) findViewById(R.id.btn_create_room);
        btn_join_room = (Button) findViewById(R.id.btn_join_room);
        btn_bot_room = (Button) findViewById(R.id.btn_bot_room);
        txt_username_home_scr = (TextView) findViewById(R.id.txt_username_home_scr);
        txt_money_home_scr = (TextView) findViewById(R.id.txt_money_home_scr);
        txt_rate_home_scr = (TextView) findViewById(R.id.txt_rate_home_scr);
        btn_sound_setting_home_scr = (ImageView) findViewById(R.id.btn_sound_setting_home_scr);
        txt_chat_global_home_scr = (TextView) findViewById(R.id.txt_chat_global_home_scr);
        bgr_txt_chat_global = (RelativeLayout) findViewById(R.id.bgr_txt_chat_global);
        btn_send_chat_global = (ImageButton) findViewById(R.id.btn_send_chat_global);
        edit_text_chat_global_home_scr = (EditText) findViewById(R.id.edit_text_chat_global_home_scr);

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
                GameService.gI().createRoomBot();
            }
        });

        if (GameSound.isPlaySound) {
            btn_sound_setting_home_scr.setImageResource(R.drawable.sound_off);
        }
        else {
            btn_sound_setting_home_scr.setImageResource(R.drawable.sound_on);
        }

        btn_sound_setting_home_scr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameSound.isPlaySound =! GameSound.isPlaySound;
                if (GameSound.isPlaySound) {
                    btn_sound_setting_home_scr.setImageResource(R.drawable.sound_off);
                }
                else {
                    btn_sound_setting_home_scr.setImageResource(R.drawable.sound_on);
                }
            }
        });

        btn_send_chat_global.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = edit_text_chat_global_home_scr.getText().toString().trim();
                if (content.length() > 0) {
                    GameService.gI().chatGlobal(content);
                    Player.getMyPlayer().money -= 5000;
                    edit_text_chat_global_home_scr.setText("");
                }
            }
        });

        mediaPlayer = MediaPlayer.create(HomeScr.this, R.raw.bgr_music);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(0.3f, 0.3f);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            };
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

    @SuppressLint("SetTextI18n")
    public void update() {
        gameTicks++;
        if (gameTicks > 9999) {
            gameTicks = 0;
        }

        txt_username_home_scr.setText(Player.getMyPlayer().username);
        txt_money_home_scr.setText(PlayerUtils.getMoneys(Player.getMyPlayer().money) + "$");
        txt_rate_home_scr.setText(PlayerUtils.getMoneys(Player.getMyPlayer().countWin) + "/" + PlayerUtils.getMoneys(Player.getMyPlayer().countGame));

        if (isShowDialogOK) {
            isShowDialogOK = false;
            GameDialog.gI().startOkDlg(context, infoDialogOk);
        }

        updateSound();

        paintChatGlobal();
    }

    public void updateSound() {
        if (GameSound.isPlaySound) {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            }
        }
        else if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void paintChatGlobal() {
        ViewGroup.LayoutParams paramsBgr = (ViewGroup.LayoutParams) bgr_txt_chat_global.getLayoutParams();

        if (textChatGlobals.size() == 0) {
            xChat = bgr_txt_chat_global.getWidth();

            paramsBgr.height = 0;
            bgr_txt_chat_global.setLayoutParams(paramsBgr);
            bgr_txt_chat_global.setVisibility(View.INVISIBLE);

            return;
        }

        paramsBgr.height = convertDpToPixels(20, context);
        bgr_txt_chat_global.setLayoutParams(paramsBgr);
        bgr_txt_chat_global.setVisibility(View.VISIBLE);

        TextChat textChat = textChatGlobals.get(0);
        if (textChat.isChatServer) {
            txt_chat_global_home_scr.setTextColor(Color.parseColor("#BAAB28"));
        }
        else {
            txt_chat_global_home_scr.setTextColor(Color.YELLOW);
        }

        if (gameTicks % 2 == 0 && xChat > 5) {
            xChat -= 5;
        }

        if (xChat <= 5) {
            if (System.currentTimeMillis() - lastTimeShowTextChat > 2000) {
                lastTimeShowTextChat = System.currentTimeMillis();
                textChatGlobals.remove(0);
                xChat = bgr_txt_chat_global.getWidth();
            }
        }
        else {
            lastTimeShowTextChat = System.currentTimeMillis();
        }

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) txt_chat_global_home_scr.getLayoutParams();
        params.setMarginStart(xChat);
        txt_chat_global_home_scr.setText(textChat.content);
        txt_chat_global_home_scr.setLayoutParams(params);
    }

    public void nextScreen() {
        isUpdate = false;
        //gameLoop.destroy();
        mediaPlayer.stop();
        startActivity(new Intent(this, GameScr.class));
        finish();
    }

    public void startOkDlg(String info) {
        isShowDialogOK = true;
        infoDialogOk = info;
    }

    public static int convertDpToPixels(float dp, Context context) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
        return px;
    }
}