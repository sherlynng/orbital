package com.example.btw.whatsup;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

/**
 * Created by BTW on 5/24/2017.
 */

public class SecondUpDigit extends Activity {
    public static final String UPDIGIT = "UPDIGIT";
    public static final int UPDIGIT_DEFAULT = 7;
    public static final String UPDIGIT2 = "UPDIGIT2";
    public static final int UPDIGIT2_DEFAULT = 8;
    int UP;
    int secondUP;
    protected boolean continueMusic = true;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondupdigit);
        UP = getIntent().getIntExtra(UPDIGIT, UPDIGIT_DEFAULT);
        secondUP = getIntent().getIntExtra(UPDIGIT2, UPDIGIT2_DEFAULT);
        final Handler handler = new Handler();
        final TextView textView = (TextView) findViewById(R.id.newUpDigit);

        textView.setText(Integer.toString(UP) + ", " + Integer.toString(secondUP));

        final Runnable counter = new Runnable() {
            @Override
            public void run() {
               finish();
            }
        };
        handler.postDelayed(counter, 1500);
    }

    @Override
    public void onBackPressed(){
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