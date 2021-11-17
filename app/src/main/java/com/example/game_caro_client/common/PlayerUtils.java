package com.example.game_caro_client.common;

public class PlayerUtils {
    public static String getMoneys(Long money)
    {
        String result = "";
        String text = money.toString();

        while(text.length() > 3)
        {
            result = "." + text.substring(text.length() - 3) + result;
            text = text.substring(0, text.length() - 3);
        }

        result = text + result;
        return result;
    }

    public static String getMoneys(int money)
    {
        String result = "";
        String text = String.valueOf(money);

        while(text.length() > 3)
        {
            result = "." + text.substring(text.length() - 3) + result;
            text = text.substring(0, text.length() - 3);
        }

        result = text + result;
        return result;
    }

}
