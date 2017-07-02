package com.example.btw.whatsup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.os.Process;
import android.widget.TextView;
import android.widget.Toast;

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
    public static final String SCORE="SCORE";
    public static final int SCORE_DEFAULT = 0;
    private int score;
    public static final String ONETWOTHREE_PAUSED = "PAUSED_123";
    private boolean paused_123;
    protected boolean continueMusic = true;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pause);

        UP = getIntent().getIntExtra(UPDIGIT, UPD_DEFAULT);
        secondUP = getIntent().getIntExtra(UPDIGIT2, UPD2_DEFAULT);
        timeLeft = getIntent().getLongExtra(TIME, 123);
        changeuptimeLeft = getIntent().getLongExtra(CHANGEUPTIME, 123);
        score = getIntent().getIntExtra(SCORE, SCORE_DEFAULT);
        paused_123 = getIntent().getBooleanExtra(ONETWOTHREE_PAUSED, false);
        current = getIntent().getIntExtra(CURRENT, CURRENT_DEFAULT);

        TextView v = (TextView) findViewById(R.id.UPD);

        if(secondUP == -1) { //SecondUP has not started
            v.setText(UP+ "");
        }
        else{
            v.setText(UP + ", " + secondUP);
        }

        TextView v2 = (TextView) findViewById(R.id.timeleft_clock);
        v2.setText("" + String.format("%02d : %02d",
                TimeUnit.MILLISECONDS.toMinutes(timeLeft),
                TimeUnit.MILLISECONDS.toSeconds(timeLeft) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeLeft))));
        TextView v3 = (TextView) findViewById(R.id.score_report);
        v3.setText(score+"");

        TextView v4 = (TextView) findViewById(R.id.currentNo);
        v4.setText(current+"");


        View resumeButton = this.findViewById(R.id.resume_btn);
        resumeButton.setOnClickListener(this);
        View contButton = this.findViewById(R.id.endgame_btn);
        contButton.setOnClickListener(this);


        if (paused_123)
            moveTaskToBack(true);
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.resume_btn:  //back to game play
                this.finish();
                break;
            case R.id.endgame_btn:
                openDialog();
                break;
        }
    }

    public void openDialog(){

        final SharedPreferences gameData = getSharedPreferences("GameData", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = gameData.edit();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Do you want to save your game? Previous unfinished game will be erased.");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        editor.putBoolean("HAS_OLD_GAME_TO_CONTINUE",true).commit();
                        Toast.makeText(Pause.this,"Game saved",Toast.LENGTH_SHORT
                        ).show();
                        backToHomePage();
                    }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editor.putBoolean("HAS_OLD_GAME_TO_CONTINUE",false).commit();
                backToHomePage();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void backToHomePage(){
        Intent i = new Intent(Pause.this, WhatsUp.class);
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