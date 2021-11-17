package com.example.game_caro_client.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.game_caro_client.R;
import com.example.game_caro_client.services.GameService;

public class GameDialog {
    public static GameDialog instance;

    public static GameDialog gI() {
        if (instance == null) {
            instance = new GameDialog();
        }
        return instance;
    }

    public void startOkDlg(Context context, String message) {
        Dialog dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_ok);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();

        windowAttributes.gravity = Gravity.BOTTOM;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(true);

        TextView txt_message_dialog_ok = dialog.findViewById(R.id.txt_message_dialog_ok);
        Button btn_dialog_ok = dialog.findViewById(R.id.btn_dialog_ok);

        txt_message_dialog_ok.setText(message);

        btn_dialog_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    public void startDlgSelectMod(Context context) {
        Dialog dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_select_mod);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();

        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(true);

        ImageButton btn_close_select_mod = dialog.findViewById(R.id.btn_close_select_mod);
        Button btn_select_mod_type_0 = dialog.findViewById(R.id.btn_select_mod_type_0);
        Button btn_select_mod_type_1 = dialog.findViewById(R.id.btn_select_mod_type_1);
        TextView txt_bets_money_setting_dialog_select_mod = dialog.findViewById(R.id.txt_bets_money_setting_dialog_select_mod);
        SeekBar seek_bar_bets_money_dialog_select_mod = dialog.findViewById(R.id.seek_bar_bets_money_dialog_select_mod);

        btn_close_select_mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        btn_select_mod_type_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameService.gI().createRoom(Integer.parseInt(txt_bets_money_setting_dialog_select_mod.getText().toString().replace("$", "").trim()), 0);
                dialog.cancel();
            }
        });

        btn_select_mod_type_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameService.gI().createRoom(Integer.parseInt(txt_bets_money_setting_dialog_select_mod.getText().toString().replace("$", "").trim()), 1);
                dialog.cancel();
            }
        });

        seek_bar_bets_money_dialog_select_mod.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                switch (i) {
                    case 0: txt_bets_money_setting_dialog_select_mod.setText("100$");
                        break;
                    case 1: txt_bets_money_setting_dialog_select_mod.setText("200$");
                        break;
                    case 2: txt_bets_money_setting_dialog_select_mod.setText("300$");
                        break;
                    case 3: txt_bets_money_setting_dialog_select_mod.setText("500$");
                        break;
                    case 4: txt_bets_money_setting_dialog_select_mod.setText("1000$");
                        break;
                    case 5: txt_bets_money_setting_dialog_select_mod.setText("2000$");
                        break;
                    case 6: txt_bets_money_setting_dialog_select_mod.setText("3000$");
                        break;
                    case 7: txt_bets_money_setting_dialog_select_mod.setText("5000$");
                        break;
                    case 8: txt_bets_money_setting_dialog_select_mod.setText("10000$");
                        break;
                    case 9: txt_bets_money_setting_dialog_select_mod.setText("100000$");
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        dialog.show();
    }

    public void startDlgSelectRoom(Context context) {
        Dialog dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_join_room);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();

        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(true);

        ImageButton btn_close_select_room = dialog.findViewById(R.id.btn_close_select_room);
        EditText txt_room_id_dialog_select_room = dialog.findViewById(R.id.txt_room_id_dialog_select_room);
        Button btn_join_room_dialog_select_room = dialog.findViewById(R.id.btn_join_room_dialog_select_room);

        btn_close_select_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        btn_join_room_dialog_select_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txt_room_id_dialog_select_room.getText().toString().length() <= 0) {
                    Toast.makeText(context, "Không được bỏ trống id", Toast.LENGTH_SHORT).show();
                }
                else {
                    GameService.gI().joinRoom(Integer.parseInt(txt_room_id_dialog_select_room.getText().toString().trim()));
                    dialog.cancel();
                }
            }
        });

        dialog.show();
    }

    public void startDlgChatRoom(Context context) {
        Dialog dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_chat_public);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();

        windowAttributes.gravity = Gravity.BOTTOM;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(true);

        ImageButton btn_close_chat_room = dialog.findViewById(R.id.btn_close_chat_room);

        btn_close_chat_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    public void startDlgSettingRoom(Context context) {
        Dialog dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_setting_game_scr);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();

        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(true);

        ImageButton btn_close_setting_game_scr = dialog.findViewById(R.id.btn_close_setting_game_scr);
        SeekBar seek_bar_bets_money_game_scr = dialog.findViewById(R.id.seek_bar_bets_money_game_scr);
        TextView txt_bets_money_setting_game_scr = dialog.findViewById(R.id.txt_bets_money_setting_game_scr);

        btn_close_setting_game_scr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        seek_bar_bets_money_game_scr.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                switch (i) {
                    case 0: txt_bets_money_setting_game_scr.setText("100$");
                        break;
                    case 1: txt_bets_money_setting_game_scr.setText("200$");
                        break;
                    case 2: txt_bets_money_setting_game_scr.setText("300$");
                        break;
                    case 3: txt_bets_money_setting_game_scr.setText("500$");
                        break;
                    case 4: txt_bets_money_setting_game_scr.setText("1000$");
                        break;
                    case 5: txt_bets_money_setting_game_scr.setText("2000$");
                        break;
                    case 6: txt_bets_money_setting_game_scr.setText("3000$");
                        break;
                    case 7: txt_bets_money_setting_game_scr.setText("5000$");
                        break;
                    case 8: txt_bets_money_setting_game_scr.setText("10000$");
                        break;
                    case 9: txt_bets_money_setting_game_scr.setText("100000$");
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        dialog.show();
    }
}
