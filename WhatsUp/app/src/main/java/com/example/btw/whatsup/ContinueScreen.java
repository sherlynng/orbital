package com.example.btw.whatsup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;


/**
 * Created by BTW on 5/24/2017.
 */

public class ContinueScreen extends Activity implements View.OnClickListener {

    public static final String MODE = "MODE";
    public static final int MODE_DEFAULT = 1;
    public static final String UPDIGIT = "UPDIGIT";
    public static final int UPD_DEFAULT = 7;
    public static final String UPDIGIT2 = "UPDIGIT2";
    public static final int UPD2_DEFAULT = -1;
    public static final String CURRENT = "CURRENT";
    public static final int CURRENT_DEFAULT = 0;
    private int UP;
    private int secondUP;
    private int current;
    public static final String TIME = "TIME";
    private long timeLeft;
    public static final String CHANGEUPTIME = "CHANGEUPTIME";
    private long changeuptimeLeft;
    public static final String ROTATETIME1 = "ROTATETIME1";
    private long rotatetimeLeft1;
    public static final String ROTATETIME2 = "ROTATETIME2";
    private long rotatetimeLeft2;
    public static final String SCORE = "SCORE";
    public static final int SCORE_DEFAULT = 0;
    private int score;
    public static final String ONETWOTHREE_PAUSED = "PAUSED_123";
    private boolean paused_123;
    protected boolean continueMusic = true;
    public static final String CALLEE = "CALLEE";
    public static final int CALLEE_DEFAULT = -1;
    private int callee;

    protected SharedPreferences gameDataLame;
    protected SharedPreferences gameDataEasy;
    protected SharedPreferences gameDataMedium;
    protected SharedPreferences gameDataHard;
    protected SharedPreferences gameDataExtreme;
    protected SharedPreferences gameDataTeamUp;
    protected SharedPreferences.Editor editorLame;
    protected SharedPreferences.Editor editorEasy;
    protected SharedPreferences.Editor editorMedium;
    protected SharedPreferences.Editor editorHard;
    protected SharedPreferences.Editor editorExtreme;
    protected SharedPreferences.Editor editorTeamUp;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.continue_screen);

        View startButton = this.findViewById(R.id.continue_start_btn);
        startButton.setBackgroundResource(R.drawable.start_btn_state);
        startButton.setOnClickListener(this);

        int mode = getIntent().getIntExtra(MODE, MODE_DEFAULT);

        gameDataLame = getSharedPreferences("gameDataLame", Context.MODE_PRIVATE);
        gameDataEasy = getSharedPreferences("gameDataEasy", Context.MODE_PRIVATE);
        gameDataMedium = getSharedPreferences("gameDataMedium", Context.MODE_PRIVATE);
        gameDataHard = getSharedPreferences("gameDataHard", Context.MODE_PRIVATE);
        gameDataExtreme = getSharedPreferences("gameDataExtreme", Context.MODE_PRIVATE);
        gameDataTeamUp = getSharedPreferences("gameDataTeamUp", Context.MODE_PRIVATE);

        TextView mode_display = (TextView) findViewById(R.id.game_mode);
        TextView v = (TextView) findViewById(R.id.UPD);
        TextView v2 = (TextView) findViewById(R.id.timeleft_clock);
        TextView v3 = (TextView) findViewById(R.id.score_report);
        TextView v4 = (TextView) findViewById(R.id.currentNo);

        switch(mode) {
            case 1:
                mode_display.setText("Lame Game");

                UP = gameDataLame.getInt("UPdigit", 1);
                score = gameDataLame.getInt("score", 0);
                current = gameDataLame.getInt("current", 1);
                timeLeft = gameDataLame.getLong("timeLeft", 120000);

                v.setText(UP + "");

                v2.setText("" + String.format("%02d : %02d",
                        TimeUnit.MILLISECONDS.toMinutes(timeLeft),
                        TimeUnit.MILLISECONDS.toSeconds(timeLeft) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeLeft))));
                v3.setText(score + "");
                v4.setText(current + "");
                break;

            case 2:
                mode_display.setText("Easy Game");

                UP = gameDataEasy.getInt("UPdigit", 1);
                score = gameDataEasy.getInt("score", 0);
                current = gameDataEasy.getInt("current", 1);
                timeLeft = gameDataEasy.getLong("timeLeft", 120000);

                v.setText(UP + "");
                v2.setText("" + String.format("%02d : %02d",
                        TimeUnit.MILLISECONDS.toMinutes(timeLeft),
                        TimeUnit.MILLISECONDS.toSeconds(timeLeft) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeLeft))));
                v3.setText(score + "");
                v4.setText(current + "");
                break;
            case 3:
                mode_display.setText("Medium Game");

                UP = gameDataMedium.getInt("UPdigit", 1);
                score = gameDataMedium.getInt("score", 0);
                current = gameDataMedium.getInt("current", 1);
                timeLeft = gameDataMedium.getLong("timeLeft", 120000);

                v.setText(UP + "");
                v2.setText("" + String.format("%02d : %02d",
                        TimeUnit.MILLISECONDS.toMinutes(timeLeft),
                        TimeUnit.MILLISECONDS.toSeconds(timeLeft) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeLeft))));
                v3.setText(score + "");
                v4.setText(current + "");
                break;
            case 4:
                mode_display.setText("Hard Game");

                UP = gameDataHard.getInt("UPdigit", 1);
                secondUP = gameDataHard.getInt("UPdigit2", -1);
                score = gameDataHard.getInt("score", 0);
                current = gameDataHard.getInt("current", 1);
                timeLeft = gameDataHard.getLong("timeLeft", 120000);

                if (secondUP == -1) { //SecondUP has not started
                    v.setText(UP + "");
                } else {
                    v.setText(UP + ", " + secondUP);
                }
                v2.setText("" + String.format("%02d : %02d",
                        TimeUnit.MILLISECONDS.toMinutes(timeLeft),
                        TimeUnit.MILLISECONDS.toSeconds(timeLeft) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeLeft))));
                v3.setText(score + "");
                v4.setText(current + "");
                break;
            case 5:
                mode_display.setText("Extreme Game");

                UP = gameDataExtreme.getInt("UPdigit", 1);
                secondUP = gameDataExtreme.getInt("UPdigit2", -1);
                score = gameDataExtreme.getInt("score", 0);
                current = gameDataExtreme.getInt("current", 1);
                timeLeft = gameDataExtreme.getLong("timeLeft", 120000);

                if (secondUP == -1) { //SecondUP has not started
                    v.setText(UP + "");
                } else {
                    v.setText(UP + ", " + secondUP);
                }
                v2.setText("" + String.format("%02d : %02d",
                        TimeUnit.MILLISECONDS.toMinutes(timeLeft),
                        TimeUnit.MILLISECONDS.toSeconds(timeLeft) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeLeft))));
                v3.setText(score + "");
                v4.setText(current + "");
                break;
            case 6:
                mode_display.setText("Team Up");

                UP = gameDataTeamUp.getInt("UPdigit", 1);
                score = gameDataTeamUp.getInt("score", 0);
                current = gameDataTeamUp.getInt("current", 1);
                timeLeft = gameDataTeamUp.getLong("timeLeft", 120000);

                v.setText(UP + "");
                v2.setText("" + String.format("%02d : %02d",
                        TimeUnit.MILLISECONDS.toMinutes(timeLeft),
                        TimeUnit.MILLISECONDS.toSeconds(timeLeft) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeLeft))));
                v3.setText(score + "");
                v4.setText(current + "");
                break;
        }
    }

    public void onClick(View v) {

        int mode = getIntent().getIntExtra(MODE, MODE_DEFAULT);

        switch (mode) {
            case 1:
                Intent lame = new Intent(this, MainLame4.class);
                this.startActivity(lame);
                break;
            case 2:
                Intent easy = new Intent(this, MainEasy4.class);
                this.startActivity(easy);
                break;
            case 3:
                Intent medium = new Intent(this, MainMedium4.class);
                this.startActivity(medium);
                break;
            case 4:
                Intent hard = new Intent(this, MainHard4.class);
                this.startActivity(hard);
                break;
            case 5:
                Intent extreme = new Intent(this, MainExtreme4.class);
                this.startActivity(extreme);
                break;
            case 6:
                Intent teamup = new Intent(this, MainTeamUp.class);
                this.startActivity(teamup);
                break;
        }
    }

    public void backToHomePage() {
        Intent i = new Intent(ContinueScreen.this, MainMenu.class);
        this.startActivity(i);
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