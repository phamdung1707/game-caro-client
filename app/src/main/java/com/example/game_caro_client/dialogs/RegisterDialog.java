package com.example.game_caro_client.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.game_caro_client.R;
import com.example.game_caro_client.services.GameService;

import java.util.regex.Pattern;

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
        EditText txt_password_reg_confirm = dialog.findViewById(R.id.txt_password_reg_confirm);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-] ");
                String username_reg = txt_username_reg.getText().toString().toLowerCase();
                String password_reg = txt_password_reg.getText().toString().toLowerCase();
                if (username_reg.length() < 5 || username_reg.length() > 10 || special.matcher(username_reg).find()) {
                    Toast.makeText(context, "Tài khoản phải từ 5 đến 10 ký tự và chỉ gồm chữ và số!", Toast.LENGTH_SHORT).show();
                }
                else if (password_reg.length() < 5 || password_reg.length() > 20 || special.matcher(password_reg).find()) {
                    Toast.makeText(context, "Mật khẩu phải từ 5 đến 20 ký tự và chỉ gồm chữ và số!", Toast.LENGTH_SHORT).show();
                }
                else if (!password_reg.equals(txt_password_reg_confirm.getText().toString())) {
                    Toast.makeText(context, "Mật khẩu không khớp!", Toast.LENGTH_SHORT).show();
                }
                else {
                    GameService.gI().register(username_reg, password_reg);
                    dialog.cancel();
                }

            }
        });

        dialog.show();
    }
}
