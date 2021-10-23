package com.example.game_caro_client.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.game_caro_client.R;
import com.example.game_caro_client.services.GameService;

public class RegisterDialog {
    public static RegisterDialog instance;

    public static RegisterDialog gI() {
        if (instance == null) {
            instance = new RegisterDialog();
        }
        return instance;
    }

    public void start(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_register);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        WindowManager.LayoutParams windowAttributes = window.getAttributes();

        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(true);

        Button btn_register = dialog.findViewById(R.id.btn_register);
        EditText txt_username_reg = dialog.findViewById(R.id.txt_username_reg);
        EditText txt_password_reg = dialog.findViewById(R.id.txt_password_reg);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameService.gI().register(txt_username_reg.getText().toString(), txt_password_reg.getText().toString());
                dialog.cancel();
            }
        });

        dialog.show();
    }
}
