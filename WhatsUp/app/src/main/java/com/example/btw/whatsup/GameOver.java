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

    public static final String REASON = "REASON";
    public static final int REASON_DEFAULT = 0;
    private int reason;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        reason = getIntent().getIntExtra(REASON, REASON_DEFAULT);
        setContentView(R.layout.gameover);
        TextView v = (TextView)findViewById(R.id.gameover_message);
        switch (reason) {
             /* REASON for ending game:
      1: Time is up
      2: did not press up
      3: no more lives
      */
            case 1:
                v.setText(getResources().getString(R.string.GameOver_Message1));
                break;
            case 2:
                v.setText(getResources().getString(R.string.GameOver_Message2));
                break;
            case 3:
                v.setText(getResources().getString(R.string.GameOver_Message3));
        }
        View resBtn = this.findViewById(R.id.restart_btn);
        resBtn.setOnClickListener(this);
        View mainBtn = this.findViewById(R.id.mainMenu_btn);
        mainBtn.setOnClickListener(this);
        SharedPreferences gameData = getSharedPreferences("GameData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = gameData.edit();
        editor.putInt("UPdigit", 1);
        editor.putInt("score", 0);
        editor.putInt("life", 3);
        editor.putInt("current", 1);
        editor.putLong("timeLeft", 120000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.restart_btn:
                Intent i = new Intent(this, ChooseUpMode.class);
                this.startActivity(i);
                break;
            case R.id.mainMenu_btn:
                Intent j = new Intent(this, WhatsUp.class);
                this.startActivity(j);
                break;
        }
    }
}