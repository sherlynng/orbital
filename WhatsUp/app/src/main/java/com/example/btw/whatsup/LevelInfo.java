package com.example.btw.whatsup;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.TextView;


/**
 * Created by BTW on 5/11/2017.
 */

public class LevelInfo extends Activity{
    protected boolean continueMusic = true;
    public static final String DIFFICULTY = "DIFFICULTY";
    private int difficulty;
    private TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_info);

        info = (TextView) findViewById(R.id.level_info);

        difficulty = getIntent().getIntExtra("DIFFICULTY", 1);

        switch (difficulty){
            case 1: //Lame
                info.setText("You have unlimited lives and hints will be provided.");
                break;
            case 2: //Easy
                info.setText("You have 3 lives and hints will not be provided.");
                break;
            case 3: //Medium
                info.setText("You have 3 lives. Grid rotates and UP digit changes during game play.");
                break;
            case 4: //Hard
                info.setText("You have 3 lives. Grid rotates and freezes. New UP digits will be added during game play.");
                break;
            case 5: //Extreme
                info.setText("You have 3 lives. Grid freezes and increases in size. New UP digits will be added during game play.");
                break;
        }
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
        MusicManager.start(this, MusicManager.MUSIC_MENU);
    }
}
