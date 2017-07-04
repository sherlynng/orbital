package com.example.btw.whatsup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by BTW on 5/21/2017.
 */

public class WhatsUp extends Activity implements OnClickListener {

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

    boolean hasOldGameToContinueLame;
    boolean hasOldGameToContinueEasy;
    boolean hasOldGameToContinueMedium;
    boolean hasOldGameToContinueHard;
    boolean hasOldGameToContinueExtreme;
    boolean hasOldGameToContinueTeamUp;

    protected boolean continueMusic = true;

//    boolean hasOldGameToContinue;
    //   int oldGameType;

    private static final int RC_SIGN_IN = 0;
    FirebaseAuth auth;

    View continueButton;
    View newButton;
    View aboutButton;
    View exitButton;
    View howToPlayButton;
    View settingsButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);

        auth = FirebaseAuth.getInstance();

        //prompt the user to login if first time running the app or not already logged in

        final String PREFS_NAME = "MyPrefsFile";
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        if (settings.getBoolean("my_first_time", true)) {
            Intent i = new Intent(this, loginlogout.class);
            this.startActivity(i);
            settings.edit().putBoolean("my_first_time", false).commit();
        }

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


        //Set up click listeners for all buttons
        hasOldGameToContinueLame = gameDataLame.getBoolean("hasoldgametocontinueLame", false);
        hasOldGameToContinueEasy = gameDataEasy.getBoolean("hasoldgametocontinueEasy", false);
        hasOldGameToContinueMedium = gameDataMedium.getBoolean("hasoldgametocontinueMedium", false);
        hasOldGameToContinueHard = gameDataHard.getBoolean("hasoldgametocontinueHard", false);
        hasOldGameToContinueExtreme = gameDataExtreme.getBoolean("hasoldgametocontinueExtreme", false);
        hasOldGameToContinueTeamUp = gameDataTeamUp.getBoolean("hasoldgametocontinueTeamUp", false);
        Log.d("SAVE", "lame+ " + Boolean.toString(hasOldGameToContinueLame));
        Log.d("SAVE", "easy+ " + Boolean.toString(hasOldGameToContinueEasy));
        Log.d("SAVE", "medium+ " + Boolean.toString(hasOldGameToContinueMedium));
        Log.d("SAVE", "hard+ " + Boolean.toString(hasOldGameToContinueHard));
        Log.d("SAVE", "extreme+ " + Boolean.toString(hasOldGameToContinueExtreme));
        Log.d("SAVE", "teamUp+ " + Boolean.toString(hasOldGameToContinueTeamUp));


        //    hasOldGameToContinue = gameData.getBoolean("hasoldgametocontinue", false);
        //  oldGameType = gameData.getInt("oldGameType", -1);

        //Continue button not visible if user not logged in or there's no old game
        continueButton = this.findViewById(R.id.continue_button);
        if (auth.getCurrentUser() == null ||
                (!hasOldGameToContinueLame &&
                        !hasOldGameToContinueEasy &&
                        !hasOldGameToContinueMedium &&
                        !hasOldGameToContinueHard &&
                        !hasOldGameToContinueExtreme &&
                        !hasOldGameToContinueTeamUp)) {
            continueButton.setVisibility(View.GONE);
        } else {
            continueButton.setBackgroundResource(R.drawable.continue_btn_state);
            continueButton.setOnClickListener(this);
        }

        newButton = this.findViewById(R.id.new_button);
        newButton.setBackgroundResource(R.drawable.new_game_btn_state);
        newButton.setOnClickListener(this);
        aboutButton = this.findViewById(R.id.about_button);
        aboutButton.setBackgroundResource(R.drawable.about_btn_state);
        aboutButton.setOnClickListener(this);
        exitButton = this.findViewById(R.id.exit_button);
        exitButton.setBackgroundResource(R.drawable.exit_btn_state);
        exitButton.setOnClickListener(this);
        howToPlayButton = this.findViewById(R.id.howtoplay_button);
        howToPlayButton.setBackgroundResource(R.drawable.howtoplay_btn_state);
        howToPlayButton.setOnClickListener(this);
        settingsButton = this.findViewById(R.id.settings_button);
        settingsButton.setBackgroundResource(R.drawable.settings_btn_state);
        settingsButton.setOnClickListener(this);

        ImageView logo = (ImageView) findViewById(R.id.whatsup_logo);
        logo.setImageResource(R.mipmap.whatsup_logo);
    }


    @Override
    protected void onResume() {
        super.onResume();
        // Music.play(this,R.raw.bgm);
        continueMusic = false;
        MusicManager.start(this, MusicManager.MUSIC_MENU);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //   Music.stop(this);
        if (!continueMusic) {
            MusicManager.pause();
        }
    }

    @Override
    public void onBackPressed() {
        openExitDialog();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.about_button:
                Intent about = new Intent(this, About.class);
                editorLame.putBoolean("continuefromlastLame", false).commit();
                editorEasy.putBoolean("continuefromlastEasy", false).commit();
                editorMedium.putBoolean("continuefromlastMedium", false).commit();
                editorHard.putBoolean("continuefromlastHard", false).commit();
                editorExtreme.putBoolean("continuefromlastExtreme", false).commit();
                editorTeamUp.putBoolean("continuefromlastTeamUp", false).commit();
                this.startActivity(about);
                break;
            case R.id.howtoplay_button:
                Intent demo = new Intent(this, PlayDemo.class);
                editorLame.putBoolean("continuefromlastLame", false).commit();
                editorEasy.putBoolean("continuefromlastEasy", false).commit();
                editorMedium.putBoolean("continuefromlastMedium", false).commit();
                editorHard.putBoolean("continuefromlastHard", false).commit();
                editorExtreme.putBoolean("continuefromlastExtreme", false).commit();
                editorTeamUp.putBoolean("continuefromlastTeamUp", false).commit();
                this.startActivity(demo);
                break;
            case R.id.continue_button:
                if (hasOldGameToContinueLame) {
                    Intent continuefromlast = new Intent(this, MainLame4.class);
                    editorLame.putBoolean("continuefromlastLame", true).commit();
                    this.startActivity(continuefromlast);
                    Log.d("SAVE", "attempt to resume Lame");

                } else if (hasOldGameToContinueEasy) {
                    Intent continuefromlast = new Intent(this, MainEasy4.class);
                    editorEasy.putBoolean("continuefromlastEasy", true).commit();
                    this.startActivity(continuefromlast);
                    Log.d("SAVE", "attempt to resume Easy");

                } else if (hasOldGameToContinueMedium) {
                    Intent continuefromlast = new Intent(this, MainMedium4.class);
                    editorMedium.putBoolean("continuefromlastMedium", true).commit();
                    this.startActivity(continuefromlast);
                    Log.d("SAVE", "attempt to resume Medium");

                } else if (hasOldGameToContinueHard) {
                    Intent continuefromlast = new Intent(this, MainHard4.class);
                    editorHard.putBoolean("continuefromlastHard", true).commit();
                    this.startActivity(continuefromlast);
                    Log.d("SAVE", "attempt to resume Hard");
                } else if (hasOldGameToContinueExtreme) {
                    Intent continuefromlast = new Intent(this, MainExtreme4.class);
                    editorExtreme.putBoolean("continuefromlastExtreme", true).commit();
                    this.startActivity(continuefromlast);
                    Log.d("SAVE", "attempt to resume Extreme");
                } else if (hasOldGameToContinueTeamUp) {
                    Intent continuefromlast = new Intent(this, MainTeamUp.class);
                    editorTeamUp.putBoolean("continuefromlast", true).commit();
                    this.startActivity(continuefromlast);
                    Log.d("SAVE", "attempt to resume TEAMUP");
                }
                break;
            case R.id.new_button:
                if (auth.getCurrentUser() != null) {
                    Intent new_Game = new Intent(this, ChooseGameMode.class);
                    editorLame.putBoolean("continuefromlastLame", false).commit();
                    editorEasy.putBoolean("continuefromlastEasy", false).commit();
                    editorMedium.putBoolean("continuefromlastMedium", false).commit();
                    editorHard.putBoolean("continuefromlastHard", false).commit();
                    editorExtreme.putBoolean("continuefromlastExtreme", false).commit();
                    editorTeamUp.putBoolean("continuefromlastTeamUp", false).commit();
                    this.startActivity(new_Game);
                } else {
                    openAuthDialog();
                }
                break;
            case R.id.exit_button:
                editorLame.putBoolean("continuefromlastLame", false).commit();
                editorEasy.putBoolean("continuefromlastEasy", false).commit();
                editorMedium.putBoolean("continuefromlastMedium", false).commit();
                editorHard.putBoolean("continuefromlastHard", false).commit();
                editorExtreme.putBoolean("continuefromlastExtreme", false).commit();
                editorTeamUp.putBoolean("continuefromlastTeamUp", false).commit();

                openExitDialog();
                break;

            case R.id.settings_button:
                Intent settings = new Intent(this, Settings.class);
                this.startActivity(settings);
                break;
        }


    }


    public void openExitDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to exit?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(WhatsUp.this, "Hope you enjoyed playing What's Up!", Toast.LENGTH_SHORT).show();
                        finishAffinity();
                    }
                });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void openAuthDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("You are not logged in yet. Game progress cannot be saved.");
        alertDialogBuilder.setPositiveButton("Log in ",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        startActivityForResult(AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setProviders(
                                        AuthUI.FACEBOOK_PROVIDER,
                                        AuthUI.EMAIL_PROVIDER,
                                        AuthUI.GOOGLE_PROVIDER)
                                .build(), RC_SIGN_IN);
                    }
                });

        alertDialogBuilder.setNegativeButton("Ignore", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent new_Game = new Intent(WhatsUp.this, ChooseGameMode.class);
                editorLame.putBoolean("continuefromlastLame", false).commit();
                editorEasy.putBoolean("continuefromlastEasy", false).commit();
                editorMedium.putBoolean("continuefromlastMedium", false).commit();
                editorHard.putBoolean("continuefromlastHard", false).commit();
                editorExtreme.putBoolean("continuefromlastExtreme", false).commit();
                editorTeamUp.putBoolean("continuefromlastTeamUp", false).commit();
                WhatsUp.this.startActivity(new_Game);
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
