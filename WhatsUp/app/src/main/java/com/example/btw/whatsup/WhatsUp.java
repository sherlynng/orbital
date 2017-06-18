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
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by BTW on 5/21/2017.
 */

public class WhatsUp extends Activity implements OnClickListener {

    protected SharedPreferences gameData;
    protected SharedPreferences.Editor editor;
    protected boolean continueMusic = true;

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
        View howToPlayButton = this.findViewById(R.id.howtoplay_button);
        howToPlayButton.setOnClickListener(this);
        View settingsButton = this.findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(this);

        ImageView logo = (ImageView) findViewById(R.id.whatsup_logo);
        logo.setImageResource(R.mipmap.whatsup_logo);

        gameData = getSharedPreferences("GameData", Context.MODE_PRIVATE);
        editor = gameData.edit();

    }

    @Override
    protected void  onResume(){
        super.onResume();
       // Music.play(this,R.raw.bgm);
        continueMusic = false;
        MusicManager.start(this, MusicManager.MUSIC_MENU);
    }

    @Override
    protected void onPause(){
        super.onPause();
     //   Music.stop(this);
        if (!continueMusic) {
            MusicManager.pause();
        }
    }

    @Override
    public void onBackPressed(){
        editor.putBoolean("CONTINUE_FROM_LAST", false).commit();
        openDialog();
    }

    public void onClick(View v) {

        boolean hasOldGameToContinue;

        switch (v.getId()) {
            case R.id.about_button:
                Intent about = new Intent(this, About.class);
                editor.putBoolean("CONTINUE_FROM_LAST", false).commit();
                this.startActivity(about);
                break;
            case R.id.howtoplay_button:
                Intent demo = new Intent(this,PlayDemo.class);
                editor.putBoolean("CONTINUE_FROM_LAST", false).commit();
                this.startActivity(demo);
                break;
            case R.id.continue_button:
                hasOldGameToContinue = gameData.getBoolean("HAS_OLD_GAME_TO_CONTINUE", false);
                if (hasOldGameToContinue) {
                    Intent continue_from_last = new Intent(this, Main4.class);
                    editor.putBoolean("CONTINUE_FROM_LAST", true).commit();
                    this.startActivity(continue_from_last);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(WhatsUp.this);
                    builder.setMessage(R.string.NoGameToContinue_message)
                            .setTitle(R.string.NoGameToContinue_title);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            Intent startNewGame = new Intent(WhatsUp.this, ChooseUpMode.class);
                            editor.putBoolean("CONTINUE_FROM_LAST", false).commit();
                            startActivity(startNewGame);
                        }
                    });
                    builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent backToMenu = new Intent(WhatsUp.this, WhatsUp.class);
                            editor.putBoolean("CONTINUE_FROM_LAST", false).commit();
                            startActivity(backToMenu);
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                break;
            case R.id.new_button:
                Intent new_Game = new Intent(this, ChooseUpMode.class);
                editor.putBoolean("CONTINUE_FROM_LAST", false).commit();
                this.startActivity(new_Game);
                break;
            case R.id.exit_button:
                editor.putBoolean("CONTINUE_FROM_LAST", false).commit();
                openDialog();
                break;

            case R.id.settings_button:
                Intent settings = new Intent(this, Settings.class);
                this.startActivity(settings);
                break;

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

}
