package com.example.btw.whatsup;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by BTW on 5/24/2017.
 */

public class WaitingForPlayer extends Activity {
    public static final String KEY = "KEY";
    public static final String KEY_DEFAULT = "hello";
    String key;
    protected boolean continueMusic = true;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waiting_for_player);
        key = getIntent().getExtras().getString(KEY, KEY_DEFAULT);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!continueMusic) {
            MusicManager.pause();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        continueMusic = false;
        MusicManager.start(this, MusicManager.MUSIC_GAME);
    }
}