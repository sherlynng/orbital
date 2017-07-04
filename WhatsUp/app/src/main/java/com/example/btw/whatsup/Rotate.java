package com.example.btw.whatsup;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by BTW on 5/24/2017.
 */

public class Rotate extends Activity {
    public static final String UPDIGIT = "UPDIGIT";
    public static final int UPDIGIT_DEFAULT = 7;
    int UP;
    protected boolean continueMusic = true;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rotate);
        final Handler handler = new Handler();
        final Runnable counter = new Runnable() {
            @Override
            public void run() {
                finish();
            }
        };
        handler.postDelayed(counter, 2500);
    }

    @Override
    public void onBackPressed() {
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