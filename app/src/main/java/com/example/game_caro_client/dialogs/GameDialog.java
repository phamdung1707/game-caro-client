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
import android.widget.RelativeLayout;
import android.widget.TextView;

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
}
