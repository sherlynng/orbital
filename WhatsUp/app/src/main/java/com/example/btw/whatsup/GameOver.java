package com.example.btw.whatsup;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by BTW on 5/11/2017.
 */

public class GameOver extends Activity implements View.OnClickListener {

    protected SharedPreferences gameData;
    protected SharedPreferences.Editor editor;
    public static final String REASON = "REASON";
    public static final int REASON_DEFAULT = 0;
    private int reason;
    protected boolean continueMusic = true;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        gameData=getSharedPreferences("GameData",Context.MODE_PRIVATE);
        editor=gameData.edit();
        reason = getIntent().getIntExtra(REASON, REASON_DEFAULT);
        setContentView(R.layout.gameover);
        TextView message = (TextView)findViewById(R.id.gameover_message);
        TextView yourScore = (TextView)findViewById(R.id.yourScore_text);
        TextView yourBestScore = (TextView)findViewById(R.id.yourBestScore_text);
        yourScore.setText(gameData.getInt("score", 0)+"");
        yourBestScore.setText( gameData.getInt("bestScore", 0)+"");
        switch (reason) {
             /* REASON for ending game:
      1: Time is up
      2: did not press up
      3: no more lives
      */
            case 1:
                message.setText(getResources().getString(R.string.GameOver_Message1));

                break;
            case 2:
              //  message.setText(getResources().getString(R.string.GameOver_Message2));

//                break;
            case 3:
                message.setText(getResources().getString(R.string.GameOver_Message3));
        }
        View resBtn = this.findViewById(R.id.restart_btn);
        resBtn.setOnClickListener(this);
        View mainBtn = this.findViewById(R.id.mainMenu_btn);
        mainBtn.setOnClickListener(this);
        editor.putInt("UPdigit", 1);
        editor.putInt("score", 0);
        editor.putInt("life", 3);
        editor.putInt("current", 1);
        editor.putLong("timeLeft", 120000);
        editor.putBoolean("HAS_OLD_GAME_TO_CONTINUE",false).commit();
        editor.putBoolean("CONTINUE_FROM_LAST", false).commit();
    }

    @Override
    public void onClick(View v) {
        SharedPreferences  gameData=getSharedPreferences("GameData",Context.MODE_PRIVATE);

        switch (v.getId()) {
            case R.id.restart_btn:
                Intent i = new Intent(this, ChooseLevel.class);
                this.startActivity(i);
                break;
            case R.id.mainMenu_btn:
                Intent j = new Intent(this, WhatsUp.class);
                this.startActivity(j);
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
        MusicManager.start(this, MusicManager.MUSIC_END_GAME);
    }
}