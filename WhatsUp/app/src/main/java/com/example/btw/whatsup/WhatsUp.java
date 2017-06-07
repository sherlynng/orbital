package com.example.btw.whatsup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

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

        ImageView pause = (ImageView) findViewById(R.id.whatsup_logo);
        pause.setImageResource(R.mipmap.whatsup_logo);
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
                openDialog();
                break;

            //More buttons to go
        }
    }

    public void openDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to exit?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(WhatsUp.this,"Hope you enjoyed playing What's Up!",Toast.LENGTH_LONG).show();
                        finishAffinity();
                    }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
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
