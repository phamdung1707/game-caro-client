package com.example.game_caro_client.models;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import com.example.game_caro_client.R;
import com.example.game_caro_client.controllers.GameController;

public class GameSound {
    public static boolean isPlaySound = true;
    private static final int MAX_STREAMS = 100;
    private SoundPool soundPool;
    private int sound_click_id;
    private int sound_win_id;
    private int sound_lose_id;
    public static GameSound instance = new GameSound();

    public static GameSound gI() {
        if (instance == null) {
            instance = new GameSound();
        }
        return instance;
    }

    public void init(Context context) {
        AudioAttributes audioAttrib = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        SoundPool.Builder builder= new SoundPool.Builder();
        builder.setAudioAttributes(audioAttrib).setMaxStreams(MAX_STREAMS);

        soundPool = builder.build();

        sound_click_id = soundPool.load(context, R.raw.click,1);

        sound_win_id = soundPool.load(context, R.raw.win,1);

        sound_lose_id = soundPool.load(context, R.raw.lose,1);
    }

    public void click()  {
        if (!isPlaySound) {
            return;
        }
        soundPool.play(sound_click_id, 1f, 1f, 1, 0, 1f);
    }

    public void win()  {
        if (!isPlaySound) {
            return;
        }
        soundPool.play(sound_win_id, 1f, 1f, 1, 0, 1f);
    }

    public void lose()  {
        if (!isPlaySound) {
            return;
        }
        soundPool.play(sound_win_id, 1f, 1f, 1, 0, 1f);
    }
}
