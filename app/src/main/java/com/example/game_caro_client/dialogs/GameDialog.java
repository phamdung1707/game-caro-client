package com.example.game_caro_client.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.game_caro_client.R;
import com.example.game_caro_client.controllers.GameController;
import com.example.game_caro_client.models.GameSound;
import com.example.game_caro_client.models.Player;
import com.example.game_caro_client.models.Room;
import com.example.game_caro_client.services.GameService;

public class GameDialog {
    public static GameDialog instance;
    public static final int ACTION_EXIT_ROOM = 0;
    public static final int ACTION_SET_MONEY = 1;
    public static int money;

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

    public void startOkDlgWithActionBackScreen(Context context, String message) {
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
                GameController.gI().gameScr.backScreen();
                dialog.cancel();
            }
        });

        dialog.show();
    }

    public void startYesNoDlgWithAction(Context context, String message, int action) {
        Dialog dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_yesno);

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

        TextView txt_message_dialog_yes_no = dialog.findViewById(R.id.txt_message_dialog_yes_no);
        Button btn_dialog_no = dialog.findViewById(R.id.btn_dialog_no);
        Button btn_dialog_yes = dialog.findViewById(R.id.btn_dialog_yes);

        txt_message_dialog_yes_no.setText(message);

        btn_dialog_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        btn_dialog_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (action == GameDialog.ACTION_EXIT_ROOM) {
                    if (Room.isPlayWithBot) {
                        GameService.gI().exitRoomBot();
                    }
                    else {
                        GameService.gI().exitRoom();
                    }
                    GameController.gI().gameScr.backScreen();
                }
                else if (action == GameDialog.ACTION_SET_MONEY) {
                    GameService.gI().setMoney(money);
                }

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
        Button btn_chat_public_dialog = dialog.findViewById(R.id.btn_chat_public_dialog);
        Button btn_chat_global_dialog = dialog.findViewById(R.id.btn_chat_global_dialog);
        TextView txt_chat_public = dialog.findViewById(R.id.txt_chat_public);

        btn_chat_public_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = txt_chat_public.getText().toString().trim();
                if (content.equals("")) {
                    Toast.makeText(context, "Vui lòng nhập nội dung!", Toast.LENGTH_SHORT).show();
                }
                else {
                    GameService.gI().chat(content);
                    dialog.cancel();
                }
            }
        });

        btn_chat_global_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = txt_chat_public.getText().toString().trim();
                if (content.equals("")) {
                    Toast.makeText(context, "Vui lòng nhập nội dung!", Toast.LENGTH_SHORT).show();
                }
                else if (Player.getMyPlayer().money < 5000) {
                    Toast.makeText(context, "Bạn không đủ tiền để chat thế giới", Toast.LENGTH_SHORT).show();
                }
                else {
                    Player.getMyPlayer().money -= 5000;
                    GameService.gI().chatGlobal(content);
                    dialog.cancel();
                }
            }
        });

        btn_close_chat_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    @SuppressLint("SetTextI18n")
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
        TextView txt_room_id_dialog_setting_game_scr = dialog.findViewById(R.id.txt_room_id_dialog_setting_game_scr);
        Button btn_ok_setting_game_scr = dialog.findViewById(R.id.btn_ok_setting_game_scr);
        ImageButton btn_sound_setting_game_scr = dialog.findViewById(R.id.btn_sound_setting_game_scr);

        txt_room_id_dialog_setting_game_scr.setText(String.valueOf(Room.roomId));
        txt_bets_money_setting_game_scr.setText(Room.money + "$");

        if (txt_bets_money_setting_game_scr.getText().toString().equals("100$")) {
            seek_bar_bets_money_game_scr.setProgress(0);
        }
        else if (txt_bets_money_setting_game_scr.getText().toString().equals("200$")) {
            seek_bar_bets_money_game_scr.setProgress(1);
        }
        else if (txt_bets_money_setting_game_scr.getText().toString().equals("300$")) {
            seek_bar_bets_money_game_scr.setProgress(2);
        }
        else if (txt_bets_money_setting_game_scr.getText().toString().equals("500$")) {
            seek_bar_bets_money_game_scr.setProgress(3);
        }
        else if (txt_bets_money_setting_game_scr.getText().toString().equals("1000$")) {
            seek_bar_bets_money_game_scr.setProgress(4);
        }
        else if (txt_bets_money_setting_game_scr.getText().toString().equals("2000$")) {
            seek_bar_bets_money_game_scr.setProgress(5);
        }
        else if (txt_bets_money_setting_game_scr.getText().toString().equals("3000$")) {
            seek_bar_bets_money_game_scr.setProgress(6);
        }
        else if (txt_bets_money_setting_game_scr.getText().toString().equals("5000$")) {
            seek_bar_bets_money_game_scr.setProgress(7);
        }
        else if (txt_bets_money_setting_game_scr.getText().toString().equals("10000$")) {
            seek_bar_bets_money_game_scr.setProgress(8);
        }
        else {
            seek_bar_bets_money_game_scr.setProgress(9);
        }

        btn_ok_setting_game_scr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Room.isStarted) {
                    Toast.makeText(context, "Trận đấu đang diễn ra!", Toast.LENGTH_SHORT).show();
                }
                else  {
                    if (Room.isPlayWithBot) {
                        Toast.makeText(context, "Không thể thay đổi khi đấu với máy", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        int money = Integer.parseInt(txt_bets_money_setting_game_scr.getText().toString().replace("$", "").trim());
                        if (Room.money == money) {
                            Toast.makeText(context, "Mức cược hiện tại đang là " + money + "$", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            GameDialog.money = money;
                            startYesNoDlgWithAction(context, "Thay đổi mức tiền cược thành " + money + "$ ?", GameDialog.ACTION_SET_MONEY);
                            dialog.cancel();
                        }
                    }
                }
            }
        });

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

        if (GameSound.isPlaySound) {
            btn_sound_setting_game_scr.setImageResource(R.drawable.sound_off);
        }
        else {
            btn_sound_setting_game_scr.setImageResource(R.drawable.sound_on);
        }

        btn_sound_setting_game_scr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameSound.isPlaySound =! GameSound.isPlaySound;
                if (GameSound.isPlaySound) {
                    btn_sound_setting_game_scr.setImageResource(R.drawable.sound_off);
                }
                else {
                    btn_sound_setting_game_scr.setImageResource(R.drawable.sound_on);
                }
            }
        });

        dialog.show();
    }
}
