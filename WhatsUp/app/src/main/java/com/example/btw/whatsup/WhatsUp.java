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
import android.view.View.OnClickListener;

/**
 * Created by BTW on 5/21/2017.
 */

public class WhatsUp extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        //Set up click listeners for all buttons
       View continueButton = this.findViewById(R.id.continue_button);
        continueButton.setOnClickListener(this);
        View newButton = this.findViewById(R.id.new_button);
        newButton.setOnClickListener(this);
        View aboutButton = this.findViewById(R.id.about_button);
        aboutButton.setOnClickListener(this);
        View exitButton = this.findViewById(R.id.exit_button);
        exitButton.setOnClickListener(this);

    }


    public void onClick(View v) {
        SharedPreferences gameData = getSharedPreferences("GameData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = gameData.edit();
        switch (v.getId()) {
            case R.id.about_button:
                Intent about = new Intent(this, About.class);
                editor.putBoolean("CONTINUE_FROM_LAST", false).commit();
                this.startActivity(about);
                break;
            case R.id.continue_button:
              Intent continue_from_last = new Intent(this,Main.class);
                editor.putBoolean("CONTINUE_FROM_LAST", true).commit();
                this.startActivity(continue_from_last);
                break;
            case R.id.new_button:
                Intent new_Game = new Intent(this, ChooseUpMode.class);
                editor.putBoolean("CONTINUE_FROM_LAST", false).commit();
                this.startActivity(new_Game);
                break;
            case R.id.exit_button:
                editor.putBoolean("CONTINUE_FROM_LAST", false).commit();
                finishAffinity();
                break;

            //More buttons to go
        }
    }

    /**
     * Start a new game with given difficulty
     */
    private void startGame() {
       // Log.d(TAG, "clicked on " + i);
        //Start game here
        Intent intent= new Intent(WhatsUp.this,ChooseUpMode.class);
     //   intent.putExtra(Game.KEY_DIFFICULTY,i); //extraData is a map of key(string)/value(in this case int) pairs
        startActivity(intent);
    }


}
