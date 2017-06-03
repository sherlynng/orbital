package com.example.btw.whatsup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
        switch (v.getId()) {
            case R.id.about_button:
                //must create an instance of Intent before running the activity
                Intent i = new Intent(this, About.class);
                this.startActivity(i);
                break;
            case R.id.continue_button:
              //  startGame(Game.DIFFICULTY_CONTINUE);
                break;
            case R.id.new_button:
                Intent j = new Intent(this, ChooseUpMode.class);
                this.startActivity(j);
                break;
            case R.id.exit_button:
                finish();
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
