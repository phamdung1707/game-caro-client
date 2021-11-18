package com.example.game_caro_client.common;

public class PlayerUtils {
    public static String getMoneys(Long money)
    {
        StringBuilder result = new StringBuilder();
        String text = money.toString();

        while(text.length() > 3)
        {
            result.insert(0, "." + text.substring(text.length() - 3));
            text = text.substring(0, text.length() - 3);
        }

        result.insert(0, text);
        return result.toString();
    }

    public static String getMoneys(int money)
    {
        StringBuilder result = new StringBuilder();
        String text = String.valueOf(money);

        while(text.length() > 3)
        {
            result.insert(0, "." + text.substring(text.length() - 3));
            text = text.substring(0, text.length() - 3);
        }

        result.insert(0, text);
        return result.toString();
    }

}
