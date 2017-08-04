package com.example.btw.whatsup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.os.Process;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;



/**
 * Created by BTW on 5/24/2017.
 */

public class Pause extends Activity implements View.OnClickListener {

    public static final String UPDIGIT = "UPDIGIT";
    public static final int UPD_DEFAULT = 7;
    public static final String UPDIGIT2 = "UPDIGIT2";
    public static final int UPD2_DEFAULT = -1;
    public static final String CURRENT = "CURRENT";
    public static final int CURRENT_DEFAULT = 0;
    // private int oldGameType;
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

    FirebaseAuth auth;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pause);

   //     auth = FirebaseAuth.getInstance();

        UP = getIntent().getIntExtra(UPDIGIT, UPD_DEFAULT);
        secondUP = getIntent().getIntExtra(UPDIGIT2, UPD2_DEFAULT);
        timeLeft = getIntent().getLongExtra(TIME, 123);
        changeuptimeLeft = getIntent().getLongExtra(CHANGEUPTIME, 123);
        current = getIntent().getIntExtra(CURRENT, CURRENT_DEFAULT);
        score = getIntent().getIntExtra(SCORE, SCORE_DEFAULT);
        paused_123 = getIntent().getBooleanExtra(ONETWOTHREE_PAUSED, false);
        callee=getIntent().getIntExtra(CALLEE,CALLEE_DEFAULT);

        gameDataLame = getSharedPreferences("gameDataLame", Context.MODE_PRIVATE);
        gameDataEasy = getSharedPreferences("gameDataEasy", Context.MODE_PRIVATE);
        gameDataMedium = getSharedPreferences("gameDataMedium", Context.MODE_PRIVATE);
        gameDataHard = getSharedPreferences("gameDataHard", Context.MODE_PRIVATE);
        gameDataExtreme = getSharedPreferences("gameDataExtreme", Context.MODE_PRIVATE);
        gameDataTeamUp = getSharedPreferences("gameDataTeamUp", Context.MODE_PRIVATE);
        editorLame = gameDataLame.edit();
        editorEasy = gameDataEasy.edit();
        editorMedium = gameDataMedium.edit();
        editorHard = gameDataHard.edit();
        editorExtreme = gameDataExtreme.edit();
        editorTeamUp = gameDataTeamUp.edit();

        TextView v = (TextView) findViewById(R.id.UPD);
        if (secondUP == -1) { //SecondUP has not started
            v.setText(UP + "");
        } else {
            v.setText(UP + ", " + secondUP);
        }

        TextView v2 = (TextView) findViewById(R.id.timeleft_clock);
        v2.setText("" + String.format("%02d : %02d",
                TimeUnit.MILLISECONDS.toMinutes(timeLeft),
                TimeUnit.MILLISECONDS.toSeconds(timeLeft) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeLeft))));
        TextView v3 = (TextView) findViewById(R.id.score_report);
        v3.setText(score + "");

        TextView v4 = (TextView) findViewById(R.id.currentNo);
        v4.setText(current + "");

        View resumeButton = this.findViewById(R.id.resume_btn);
        resumeButton.setOnClickListener(this);
        resumeButton.setBackgroundResource(R.drawable.resume_btn_state);
        View endGameButton = this.findViewById(R.id.endgame_btn);
        endGameButton.setBackgroundResource(R.drawable.endgame_btn_state);
        endGameButton.setOnClickListener(this);


        if (paused_123)
            moveTaskToBack(true);
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.resume_btn:  //back to game play
                this.finish();
                break;
            case R.id.endgame_btn:
             //   if (auth.getCurrentUser() == null){
             //       backToHomePage();
             //   }
             //   else {
                    openDialog();
             //   }
                break;
        }
    }

    public void openDialog() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Do you want to save your game? Previous unfinished game will be erased.");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Log.d("SAVE","callee= "+callee);
                        switch (callee) {
                            case 1:
                                editorLame.putBoolean("hasoldgametocontinueLame", true).commit();
                                editorEasy.putBoolean("hasoldgametocontinueEasy", false).commit();
                                editorMedium.putBoolean("hasoldgametocontinueMedium", false).commit();
                                editorHard.putBoolean("hasoldgametocontinueHard", false).commit();
                                editorExtreme.putBoolean("hasoldgametocontinueExtreme", false).commit();
                                editorTeamUp.putBoolean("hasoldgametocontinueTeamUp", false).commit();
                                Log.d("SAVE", "saved a Lame game");
                                break;
                            case 2:
                                editorLame.putBoolean("hasoldgametocontinueLame", false).commit();
                                editorEasy.putBoolean("hasoldgametocontinueEasy", true).commit();
                                editorMedium.putBoolean("hasoldgametocontinueMedium", false).commit();
                                editorHard.putBoolean("hasoldgametocontinueHard", false).commit();
                                editorExtreme.putBoolean("hasoldgametocontinueExtreme", false).commit();
                                editorTeamUp.putBoolean("hasoldgametocontinueTeamUp", false).commit();
                                Log.d("SAVE", "saved a easy game");
                                break;
                            case 3:
                                editorLame.putBoolean("hasoldgametocontinueLame", false).commit();
                                editorEasy.putBoolean("hasoldgametocontinueEasy", false).commit();
                                editorMedium.putBoolean("hasoldgametocontinueMedium", true).commit();
                                editorHard.putBoolean("hasoldgametocontinueHard", false).commit();
                                editorExtreme.putBoolean("hasoldgametocontinueExtreme", false).commit();
                                editorTeamUp.putBoolean("hasoldgametocontinueTeamUp", false).commit();
                                Log.d("SAVE", "saved a medium game");
                                break;
                            case 4:
                                editorLame.putBoolean("hasoldgametocontinueLame", false).commit();
                                editorEasy.putBoolean("hasoldgametocontinueEasy", false).commit();
                                editorMedium.putBoolean("hasoldgametocontinueMedium", false).commit();
                                editorHard.putBoolean("hasoldgametocontinueHard", true).commit();
                                editorExtreme.putBoolean("hasoldgametocontinueExtreme", false).commit();
                                editorTeamUp.putBoolean("hasoldgametocontinueTeamUp", false).commit();
                                Log.d("SAVE", " saved a hard game");
                                break;
                            case 5:
                                editorLame.putBoolean("hasoldgametocontinueLame", false).commit();
                                editorEasy.putBoolean("hasoldgametocontinueEasy", false).commit();
                                editorMedium.putBoolean("hasoldgametocontinueMedium", false).commit();
                                editorHard.putBoolean("hasoldgametocontinueHard", false).commit();
                                editorExtreme.putBoolean("hasoldgametocontinueExtreme", true).commit();
                                editorTeamUp.putBoolean("hasoldgametocontinueTeamUp", false).commit();
                                Log.d("SAVE", "saved a extreme game");
                                break;
                            case 6:
                                editorLame.putBoolean("hasoldgametocontinueLame", false).commit();
                                editorEasy.putBoolean("hasoldgametocontinueEasy", false).commit();
                                editorMedium.putBoolean("hasoldgametocontinueMedium", false).commit();
                                editorHard.putBoolean("hasoldgametocontinueHard", false).commit();
                                editorExtreme.putBoolean("hasoldgametocontinueExtreme", false).commit();
                                editorTeamUp.putBoolean("hasoldgametocontinueTeamUp", true).commit();
                                Log.d("SAVE", "saved a teamUp game");
                        }
                        Toast.makeText(Pause.this, "Game saved", Toast.LENGTH_SHORT
                        ).show();
                        backToHomePage();
                    }
                });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (callee) {
                    case 1:
                        editorLame.putBoolean("hasoldgametocontinueLame", false).commit();
                        break;
                    case 2:
                        editorEasy.putBoolean("hasoldgametocontinueEasy", false).commit();
                        break;
                    case 3:
                        editorMedium.putBoolean("hasoldgametocontinueMedium", false).commit();
                        break;
                    case 4:
                        editorHard.putBoolean("hasoldgametocontinueHard", false).commit();
                        break;
                    case 5:
                        editorExtreme.putBoolean("hasoldgametocontinueExtreme", false).commit();
                        break;
                    case 6:
                        editorTeamUp.putBoolean("hasoldgametocontinueTeamUp", false).commit();
                }
                backToHomePage();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void backToHomePage() {
        Intent i = new Intent(Pause.this, MainMenu.class);
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