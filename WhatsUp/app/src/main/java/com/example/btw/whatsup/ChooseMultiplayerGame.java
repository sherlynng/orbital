package com.example.btw.whatsup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

import static com.firebase.ui.auth.ui.AcquireEmailHelper.RC_SIGN_IN;

/**
 * Created by BTW on 6/11/2017.
 */

public class ChooseMultiplayerGame extends Activity implements OnClickListener, View.OnLongClickListener {
    protected boolean continueMusic = true;
    FirebaseAuth auth;
    protected SharedPreferences settings;
    String game_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twoplayer);

        auth = FirebaseAuth.getInstance();

        final String PREFS_NAME = "MyPrefsFile";
        settings = getSharedPreferences(PREFS_NAME, 0);

        View hurryUpBtn = this.findViewById(R.id.hurryUp_button);
        hurryUpBtn.setBackgroundResource(R.drawable.hurryup_btn_state);
        hurryUpBtn.setOnClickListener(this);
        hurryUpBtn.setOnLongClickListener(this);
        View upAndDownBtn = this.findViewById(R.id.UpAndDown_button);
        upAndDownBtn.setBackgroundResource(R.drawable.updown_btn_state);
        upAndDownBtn.setOnClickListener(this);
        upAndDownBtn.setOnLongClickListener(this);
        View teamUpBtn = this.findViewById(R.id.TeamUp_button);
        teamUpBtn.setBackgroundResource(R.drawable.teamup_btn_state);
        teamUpBtn.setOnClickListener(this);
        teamUpBtn.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.hurryUp_button:
                /*
                if (auth.getCurrentUser() != null) {
                    Intent game = new Intent(this, HurryUpGameStates.class);
                    this.startActivity(game);
                } else {
                    openAuthDialog();
                } */
                game_id = settings.getString("game_id", "");
                if (isOnline()) {
                    if(game_id.equals("")){
                        openGameIdDialog();
                        openSetGameIdDialog();
                    }
                    else {
                        Intent game = new Intent(this, HurryUpGameStates.class);
                        this.startActivity(game);
                    }
                } else{
                    openConnectInternetDialog();
                }

                break;

            case R.id.UpAndDown_button:
                /*
                if (auth.getCurrentUser() != null) {
                    Intent game2 = new Intent(this, UpDownGameStates.class);
                    this.startActivity(game2);
                } else {
                    openAuthDialog();
                }
                */
                game_id = settings.getString("game_id", "");
                if (isOnline()) {
                    if(game_id.equals("")){
                        openGameIdDialog();
                        openSetGameIdDialog();
                    }
                    else {
                        Intent game2 = new Intent(this, UpDownGameStates.class);
                        this.startActivity(game2);
                    }
                } else {
                    openConnectInternetDialog();
                }
                break;

            case R.id.TeamUp_button:
                Intent game3 = new Intent(this, MainTeamUp.class);
                this.startActivity(game3);
                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.hurryUp_button:
                Intent hurryUp = new Intent(this, LevelInfo.class);
                hurryUp.putExtra(LevelInfo.DIFFICULTY, 6);
                this.startActivity(hurryUp);
                break;
            case R.id.UpAndDown_button:
                Intent upDown = new Intent(this, LevelInfo.class);
                upDown.putExtra(LevelInfo.DIFFICULTY, 7);
                this.startActivity(upDown);
                break;
            case R.id.TeamUp_button:
                Intent teamUp = new Intent(this, LevelInfo.class);
                teamUp.putExtra(LevelInfo.DIFFICULTY, 8);
                this.startActivity(teamUp);
                break;
        }
        return true;
    }

    public void openAuthDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Unable to play without logging in.");
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

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void openSetGameIdDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Please set your Game ID.");
        alertDialogBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void openGameIdDialog(){
        //    AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //   LayoutInflater inflater = this.getLayoutInflater();
        //   final AlertDialog.Builder builder1 = builder.setView(inflater.inflate(game_id), null);

        final LayoutInflater layoutInflater = this.getLayoutInflater();
        final View view = layoutInflater.inflate(R.layout.game_id, null);
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Game ID");
        //   alertDialog.setIcon("Icon id here");
        alertDialog.setCancelable(false);
        //   Constant.alertDialog.setMessage("Your Message Here");

        final EditText gameID_text = (EditText) view.findViewById(R.id.game_id);
        String prev_game_id = settings.getString("game_id", null);
        if(prev_game_id != null){
            gameID_text.setText(prev_game_id);
        }

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                String game_id = gameID_text.getText().toString();
                settings.edit().putString("game_id", game_id).commit();
            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        alertDialog.setView(view);
        alertDialog.show();
    }

    public void openConnectInternetDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Unable to play without Internet connection.");
        alertDialogBuilder.setPositiveButton("Got it!",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    protected boolean isOnline(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
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