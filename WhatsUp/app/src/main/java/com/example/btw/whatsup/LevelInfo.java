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
            case 6: //Hurry Up
                info.setText("There is no time limit. Two players will play the same game at the same time. Challenge to see who reaches 100 first.");
                break;
            case 7: //Up Down
                info.setText("There is no time limit. For the first round, player A starts from increasing numbers (1,2,3…) while player B starts from decreasing numbers (100,99, 98…). Depends on where they meet, the players will get corresponding scores. They then switch position (player A starts from decreasing numbers and player B starts from increasing numbers). Combine scores from two rounds of game and determine winner.");
                break;
            case 8: //Team Up
                info.setText("There is a time timit of 2 minutes. The screen will display UP button on each end of the screen. Each player is in charge of half the grid and one UP button. Which UP button is to be pressed depends on the location of the UP number, for example, if the UP number appears on lower half of the screen, then the player in charge of the lower UP button must press it.");
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
