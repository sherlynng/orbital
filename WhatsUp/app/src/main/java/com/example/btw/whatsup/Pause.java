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
    private int UP;
    public static final String TIME = "TIME";
    private long timeLeft;
    public static final String ONETWOTHREE_PAUSED = "PAUSED_123";
    private boolean paused_123;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pause);

        UP = getIntent().getIntExtra(UPDIGIT, UPD_DEFAULT);
        timeLeft = getIntent().getLongExtra(TIME, 123);
        paused_123 = getIntent().getBooleanExtra(ONETWOTHREE_PAUSED, false);




        TextView v = (TextView) findViewById(R.id.UPD);
        v.setText(UP + "");
        TextView v2 = (TextView) findViewById(R.id.timeleft_clock);
        v2.setText("" + String.format("%02d : %02d",
                TimeUnit.MILLISECONDS.toMinutes(timeLeft),
                TimeUnit.MILLISECONDS.toSeconds(timeLeft) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeLeft))));

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
                finish();
                break;
            case R.id.endgame_btn:  //back to start menu. save game data to continue next time
              //  Intent continueNextTime = new Intent(this, WhatsUp.class);
                //editor.putBoolean("HAS_OLD_GAME_TO_CONTINUE",true).commit();
               // this.startActivity(continueNextTime);
                openDialog();
                break;
        }
    }

    public void openDialog(){

        final SharedPreferences gameData = getSharedPreferences("GameData", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = gameData.edit();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Do you want to save your game?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        editor.putBoolean("HAS_OLD_GAME_TO_CONTINUE",true).commit();
                        Toast.makeText(Pause.this,"Game saved",Toast.LENGTH_LONG).show();
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

}